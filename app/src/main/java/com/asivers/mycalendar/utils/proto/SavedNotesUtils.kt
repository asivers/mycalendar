package com.asivers.mycalendar.utils.proto

import android.content.Context
import com.asivers.mycalendar.data.NoteInfo
import com.asivers.mycalendar.data.SelectedDateInfo
import com.asivers.mycalendar.data.proto.SavedNotesOuterClass.SavedNotes.*
import com.asivers.mycalendar.serializers.savedNotesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

fun areNotificationsExist(ctx: Context): Boolean {
    return runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .flatMap { it.forDayList }
        .flatMap { it.notesList }
        .find { !it.notificationTimeNull } != null
}

fun getMaxNoteId(ctx: Context): Int? {
    return runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .flatMap { it.forDayList }
        .flatMap { it.notesList }
        .maxOfOrNull { it.id }
}

fun getDaysWithNotesForMonth(
    ctx: Context,
    year: Int,
    monthValue: Int
): List<Int> {
    return runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .find { it.monthValue == monthValue }
        ?.forDayList
        ?.filter { it.notesList != null && containsNotesForYear(it.notesList, year) }
        ?.map { it.dayOfMonth }
        ?.sorted()
        ?: emptyList()
}

private fun containsNotesForYear(notesList: List<Note>, year: Int): Boolean {
    return notesList.map { it.forYear }.any { it == 0 || it == year }
}

fun getNotes(
    ctx: Context,
    selectedDateInfo: SelectedDateInfo
): List<NoteInfo> {
    val allNotesForMonthAndDay = runBlocking { ctx.savedNotesDataStore.data.first() }
        .forMonthList
        .find { it.monthValue == selectedDateInfo.monthValue }
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
    id: Int,
    msg: String,
    isEveryYear: Boolean,
    notificationTime: com.asivers.mycalendar.data.NotificationTime?
): NoteInfo {
    val note = Note.newBuilder()
        .setId(id)
        .setMsg(msg)
        .setForYear(if (isEveryYear) 0 else selectedDateInfo.year)
        .setNotificationTime(notificationTime)
        .build()

    changeNotesList(
        ctx = ctx,
        monthValue = selectedDateInfo.monthValue,
        dayOfMonth = selectedDateInfo.dayOfMonth,
        operation = { forDayBuilder ->
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
    notificationTime: com.asivers.mycalendar.data.NotificationTime?
): NoteInfo {
    val note = Note.newBuilder()
        .setId(id)
        .setMsg(msg)
        .setForYear(if (isEveryYear) 0 else selectedDateInfo.year)
        .setNotificationTime(notificationTime)
        .build()

    changeNotesList(
        ctx = ctx,
        monthValue = selectedDateInfo.monthValue,
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
        monthValue = selectedDateInfo.monthValue,
        dayOfMonth = selectedDateInfo.dayOfMonth,
        operation = { forDayBuilder ->
            removeNoteFromBuilder(forDayBuilder, id)
        }
    )
}

private fun changeNotesList(
    ctx: Context,
    monthValue: Int,
    dayOfMonth: Int,
    operation: (ForDay.Builder) -> Unit
) {
    runBlocking {
        ctx.savedNotesDataStore.updateData { currentSavedNotes ->
            val savedNotesBuilder = currentSavedNotes.toBuilder()
            val forMonthBuilder = getAndRemoveForMonth(savedNotesBuilder, monthValue)
                ?.toBuilder()
                ?: ForMonth.newBuilder().setMonthValue(monthValue)
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
    monthValue: Int
): ForMonth? {
    savedNotesBuilder.forMonthList.forEachIndexed { indexInList, forMonth ->
        if (forMonth.monthValue == monthValue) {
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

private fun Note.Builder.setNotificationTime(
    notificationTime: com.asivers.mycalendar.data.NotificationTime?
): Note.Builder {
    if (notificationTime == null) {
        this.setNotificationTimeNull(true)
    } else {
        this.setNotificationTimeValue(
            NotificationTime.newBuilder()
                .setHour(notificationTime.hour)
                .setMinute(notificationTime.minute)
                .build()
        )
    }
    return this
}
