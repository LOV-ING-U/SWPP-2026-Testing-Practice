package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.swpp.tododapp.data.Task
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {

    @Test
    fun setDueDateLabel_activeDueToday_showsDDay() = onMain {
        // Given a visible TextView with old text and an active task due today
        val textView = textView("old", View.VISIBLE)
        val today = LocalDate.now()

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = false, dueDate = today))

        // Then it shows D-Day and is visible
        assertLabel(textView, "D-Day", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_activeDueTomorrow_showsDMinusOne() = onMain {
        // Given a hidden TextView with old text and an active task due tomorrow
        val textView = textView("old", View.GONE)
        val today = LocalDate.now()

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = false, dueDate = today.plusDays(1)))

        // Then it shows D-1 and is visible
        assertLabel(textView, "D-1", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_activeOverdueByOneDay_showsDPlusOne() = onMain {
        // Given a hidden TextView with old text and an active task overdue by one day
        val textView = textView("old", View.GONE)
        val today = LocalDate.now()

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = false, dueDate = today.minusDays(1)))

        // Then it shows D+1 and is visible
        assertLabel(textView, "D+1", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_completedWithoutDueDate_showsClear() = onMain {
        // Given a hidden TextView with old text and a completed task without a due date
        val textView = textView("old", View.GONE)

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = true, dueDate = null))

        // Then it shows CLEAR and is visible
        assertLabel(textView, "CLEAR", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_nullTask_clearsAndHidesLabel() = onMain {
        // Given a visible TextView with D-1 text and no task
        val textView = textView("D-1", View.VISIBLE)

        // When the due-date label is bound
        setDueDateLabel(textView, null)

        // Then it is empty and gone
        assertLabel(textView, "", View.GONE)
    }

    @Test
    fun setDueDateLabel_oneDayBeforeTodayBoundary_showsDPlusOne() = onMain {
        // Given a hidden TextView with old text and an active task due yesterday
        val textView = textView("old", View.GONE)
        val today = LocalDate.now()

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = false, dueDate = today.minusDays(1)))

        // Then it shows D+1 and is visible
        assertLabel(textView, "D+1", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_todayBoundary_showsDDay() = onMain {
        // Given a hidden TextView with old text and an active task due today
        val textView = textView("old", View.GONE)
        val today = LocalDate.now()

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = false, dueDate = today))

        // Then it shows D-Day and is visible
        assertLabel(textView, "D-Day", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_oneDayAfterTodayBoundary_showsDMinusOne() = onMain {
        // Given a hidden TextView with old text and an active task due tomorrow
        val textView = textView("old", View.GONE)
        val today = LocalDate.now()

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = false, dueDate = today.plusDays(1)))

        // Then it shows D-1 and is visible
        assertLabel(textView, "D-1", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_activeWithoutDueDate_clearsAndHidesLabel() = onMain {
        // Given a visible TextView with D-3 text and an active task without a due date
        val textView = textView("D-3", View.VISIBLE)

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = false, dueDate = null))

        // Then it is empty and gone
        assertLabel(textView, "", View.GONE)
    }

    @Test
    fun setDueDateLabel_nullTaskFromClear_clearsAndHidesLabel() = onMain {
        // Given a visible TextView with CLEAR text and no task
        val textView = textView("CLEAR", View.VISIBLE)

        // When the due-date label is bound
        setDueDateLabel(textView, null)

        // Then it is empty and gone
        assertLabel(textView, "", View.GONE)
    }

    @Test
    fun setDueDateLabel_completedWithFutureDueDate_showsClear() = onMain {
        // Given a hidden TextView with old text and a completed task due tomorrow
        val textView = textView("old", View.GONE)
        val today = LocalDate.now()

        // When the due-date label is bound
        setDueDateLabel(textView, task(isCompleted = true, dueDate = today.plusDays(1)))

        // Then it shows CLEAR and is visible
        assertLabel(textView, "CLEAR", View.VISIBLE)
    }

    @Test
    fun setDueDateLabel_completedThenActiveWithoutDueDate_clearsStaleClear() = onMain {
        // Given one TextView and a completed task without a due date
        val textView = textView("old", View.GONE)
        val completedTask = task(isCompleted = true, dueDate = null)
        val activeTask = task(isCompleted = false, dueDate = null)

        // When it is first bound to the completed task and then to the active task
        setDueDateLabel(textView, completedTask)

        // Then the first binding shows CLEAR and is visible
        assertLabel(textView, "CLEAR", View.VISIBLE)
        setDueDateLabel(textView, activeTask)

        // Then the second binding clears the label and hides the same view
        assertLabel(textView, "", View.GONE)
    }

    private fun onMain(block: () -> Unit) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(block)
    }

    private fun textView(initialText: String, visibility: Int): TextView =
        TextView(ApplicationProvider.getApplicationContext()).apply {
            text = initialText
            this.visibility = visibility
        }

    private fun task(isCompleted: Boolean, dueDate: LocalDate?): Task = Task(
        title = "Test title",
        description = "Test description",
        isCompleted = isCompleted,
        id = "test-task-id",
        dueDate = dueDate
    )

    private fun assertLabel(textView: TextView, expectedText: String, expectedVisibility: Int) {
        assertEquals(expectedText, textView.text.toString())
        assertEquals(expectedVisibility, textView.visibility)
    }
}
