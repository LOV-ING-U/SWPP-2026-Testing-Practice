package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DueDateUtilsTest {

    @Test
    fun dueDateLabel_completedWithoutDueDate_returnsClear() {
        // Given a completed task with no due date and today 2026-10-14
        val result = dueDateLabel(null, LocalDate.of(2026, 10, 14), true)

        // When the label is calculated
        // Then it is CLEAR
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_completedWithFutureDueDate_returnsClear() {
        // Given a completed task due on 2026-10-15 and today 2026-10-14
        val result = dueDateLabel(LocalDate.of(2026, 10, 15), LocalDate.of(2026, 10, 14), true)

        // When the label is calculated
        // Then it is CLEAR
        assertEquals("CLEAR", result)
    }

    @Test
    fun dueDateLabel_activeWithoutDueDate_returnsEmptyString() {
        // Given an active task with no due date and today 2026-10-14
        val result = dueDateLabel(null, LocalDate.of(2026, 10, 14), false)

        // When the label is calculated
        // Then it is empty
        assertEquals("", result)
    }

    @Test
    fun dueDateLabel_activeDueToday_returnsDDay() {
        // Given an active task due today, 2026-10-14
        val result = dueDateLabel(LocalDate.of(2026, 10, 14), LocalDate.of(2026, 10, 14), false)

        // When the label is calculated
        // Then it is D-Day
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_activeDueInThreeDays_returnsDMinusThree() {
        // Given an active task due on 2026-10-17 and today 2026-10-14
        val result = dueDateLabel(LocalDate.of(2026, 10, 17), LocalDate.of(2026, 10, 14), false)

        // When the label is calculated
        // Then it is D-3
        assertEquals("D-3", result)
    }

    @Test
    fun dueDateLabel_activeOverdueByThreeDays_returnsDPlusThree() {
        // Given an active task due on 2026-10-11 and today 2026-10-14
        val result = dueDateLabel(LocalDate.of(2026, 10, 11), LocalDate.of(2026, 10, 14), false)

        // When the label is calculated
        // Then it is D+3
        assertEquals("D+3", result)
    }

    @Test
    fun dueDateLabel_activeDueOnLeapDay_returnsDMinusOne() {
        // Given an active task due on 2024-02-29 and today 2024-02-28
        val result = dueDateLabel(LocalDate.of(2024, 2, 29), LocalDate.of(2024, 2, 28), false)

        // When the label is calculated
        // Then it is D-1
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_oneDayOverdue_returnsDPlusOne() {
        // Given an active task due on 2026-10-13 and today 2026-10-14
        val result = dueDateLabel(LocalDate.of(2026, 10, 13), LocalDate.of(2026, 10, 14), false)

        // When the label is calculated
        // Then it is D+1
        assertEquals("D+1", result)
    }

    @Test
    fun dueDateLabel_overdueTodayBoundary_returnsDDay() {
        // Given an active task due on today, 2026-10-14
        val result = dueDateLabel(LocalDate.of(2026, 10, 14), LocalDate.of(2026, 10, 14), false)

        // When the label is calculated
        // Then it is D-Day
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_oneDayFuture_returnsDMinusOne() {
        // Given an active task due on 2026-10-15 and today 2026-10-14
        val result = dueDateLabel(LocalDate.of(2026, 10, 15), LocalDate.of(2026, 10, 14), false)

        // When the label is calculated
        // Then it is D-1
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_adjacentAcrossMonthEnd_returnsDMinusOne() {
        // Given an active task due on 2026-11-01 and today 2026-10-31
        val result = dueDateLabel(LocalDate.of(2026, 11, 1), LocalDate.of(2026, 10, 31), false)

        // When the label is calculated
        // Then it is D-1
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_adjacentAcrossYearEnd_returnsDMinusOne() {
        // Given an active task due on 2027-01-01 and today 2026-12-31
        val result = dueDateLabel(LocalDate.of(2027, 1, 1), LocalDate.of(2026, 12, 31), false)

        // When the label is calculated
        // Then it is D-1
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_dayBeforeLeapDay_returnsDMinusOne() {
        // Given an active task due on 2024-02-29 and today 2024-02-28
        val result = dueDateLabel(LocalDate.of(2024, 2, 29), LocalDate.of(2024, 2, 28), false)

        // When the label is calculated
        // Then it is D-1
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_leapDayToday_returnsDDay() {
        // Given an active task due on leap day 2024-02-29
        val result = dueDateLabel(LocalDate.of(2024, 2, 29), LocalDate.of(2024, 2, 29), false)

        // When the label is calculated
        // Then it is D-Day
        assertEquals("D-Day", result)
    }

    @Test
    fun dueDateLabel_dayAfterLeapDay_returnsDMinusOne() {
        // Given an active task due on 2024-03-01 and today 2024-02-29
        val result = dueDateLabel(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 2, 29), false)

        // When the label is calculated
        // Then it is D-1
        assertEquals("D-1", result)
    }

    @Test
    fun dueDateLabel_futureAcrossMonthBoundary_returnsDMinusThirtyThree() {
        // Given an active task due on 2026-11-03 and today 2026-10-01
        val result = dueDateLabel(LocalDate.of(2026, 11, 3), LocalDate.of(2026, 10, 1), false)

        // When the label is calculated
        // Then it is D-33
        assertEquals("D-33", result)
    }

    @Test
    fun dueDateLabel_pastAcrossMonthBoundary_returnsDPlusThirtyThree() {
        // Given an active task due on 2026-10-01 and today 2026-11-03
        val result = dueDateLabel(LocalDate.of(2026, 10, 1), LocalDate.of(2026, 11, 3), false)

        // When the label is calculated
        // Then it is D+33
        assertEquals("D+33", result)
    }
}
