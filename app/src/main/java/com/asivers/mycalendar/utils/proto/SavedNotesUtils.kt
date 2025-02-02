package com.asivers.mycalendar.utils.proto

import android.content.Context
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.proto.SavedNotesOuterClass.SavedNotes.*
import com.asivers.mycalendar.serializers.savedNotesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun getNotes(
    ctx: Context,
    selectedDateInfo: SelectedDateInfo
): List<NoteInfo> {
    val allNotesForMonthAndDay = runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .find { it.monthIndex == selectedDateInfo.monthIndex }
        ?.forDayList
        ?.find { it.dayOfMonth == selectedDateInfo.dayOfMonth }
        ?.notesList
        ?: listOf<Note>()
    return allNotesForMonthAndDay
        .filter { it.forYear == 0 || it.forYear == selectedDateInfo.year }
        .map { NoteInfo(it) }
        .sorted()
}

fun addNote(
    ctx: Context,
    selectedDateInfo: SelectedDateInfo,
    msg: String,
    isEveryYear: Boolean,
    isHoliday: Boolean
): NoteInfo {
    var note = Note.newBuilder()
        .setMsg(msg)
        .setForYear(if (isEveryYear) 0 else selectedDateInfo.year)
        .setIsHoliday(isHoliday)
        .build()

    changeNotesList(
        ctx = ctx,
        monthIndex = selectedDateInfo.monthIndex,
        dayOfMonth = selectedDateInfo.dayOfMonth,
        operation = { forDayBuilder ->
            val notesList = forDayBuilder.notesList
            val id = if (notesList.isEmpty()) 1 else notesList.maxOf { it.id } + 1
            note = note.toBuilder().setId(id).build()
            forDayBuilder.addNotes(note)
        }
    )

    return NoteInfo(note)
}

fun editNote(
    ctx: Context,
    selectedDateInfo: SelectedDateInfo,
    id: Int,
    msg: String,
    isEveryYear: Boolean,
    isHoliday: Boolean
): NoteInfo {
    val note = Note.newBuilder()
        .setId(id)
        .setMsg(msg)
        .setForYear(if (isEveryYear) 0 else selectedDateInfo.year)
        .setIsHoliday(isHoliday)
        .build()

    changeNotesList(
        ctx = ctx,
        monthIndex = selectedDateInfo.monthIndex,
        dayOfMonth = selectedDateInfo.dayOfMonth,
        operation = { forDayBuilder ->
            removeNoteFromBuilder(forDayBuilder, id)
            forDayBuilder.addNotes(note)
        }
    )

    return NoteInfo(note)
}

fun removeNote(
    ctx: Context,
    selectedDateInfo: SelectedDateInfo,
    id: Int
) {
    changeNotesList(
        ctx = ctx,
        monthIndex = selectedDateInfo.monthIndex,
        dayOfMonth = selectedDateInfo.dayOfMonth,
        operation = { forDayBuilder ->
            removeNoteFromBuilder(forDayBuilder, id)
        }
    )
}

private fun changeNotesList(
    ctx: Context,
    monthIndex: Int,
    dayOfMonth: Int,
    operation: (ForDay.Builder) -> Unit
) {
    runBlocking {
        ctx.savedNotesDataStore.updateData { currentSavedNotes ->
            val savedNotesBuilder = currentSavedNotes.toBuilder()
            val forMonthBuilder = getAndRemoveForMonth(savedNotesBuilder, monthIndex)
                ?.toBuilder()
                ?: ForMonth.newBuilder().setMonthIndex(monthIndex)
            val forDayBuilder = getAndRemoveForDay(forMonthBuilder, dayOfMonth)
                ?.toBuilder()
                ?: ForDay.newBuilder().setDayOfMonth(dayOfMonth)

            operation.invoke(forDayBuilder)

            forMonthBuilder.addForDay(forDayBuilder.build())
            savedNotesBuilder.addForMonth(forMonthBuilder.build()).build()
        }
    }
}

private fun getAndRemoveForMonth(
    savedNotesBuilder: Builder,
    monthIndex: Int
): ForMonth? {
    savedNotesBuilder.forMonthList.forEachIndexed { indexInList, forMonth ->
        if (forMonth.monthIndex == monthIndex) {
            savedNotesBuilder.removeForMonth(indexInList)
            return forMonth
        }
    }
    return null
}

private fun getAndRemoveForDay(
    forMonthBuilder: ForMonth.Builder,
    dayOfMonth: Int
): ForDay? {
    forMonthBuilder.forDayList.forEachIndexed { indexInList, forDay ->
        if (forDay.dayOfMonth == dayOfMonth) {
            forMonthBuilder.removeForDay(indexInList)
            return forDay
        }
    }
    return null
}

private fun removeNoteFromBuilder(
    forDayBuilder: ForDay.Builder,
    id: Int
) {
    forDayBuilder.notesList.forEachIndexed { indexInList, note ->
        if (note.id == id) {
            forDayBuilder.removeNotes(indexInList)
        }
    }
}
