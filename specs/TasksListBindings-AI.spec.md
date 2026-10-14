# Spec: `TasksListBindings.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `fun setDueDateLabel(textView: TextView, task: Task?)`

**Intent**: Bind a task's current due-date label into its list-row `TextView`. A non-empty label is shown; an absent label clears stale text and hides the view.

**Pre-conditions**
- `textView` is an initialized Android `TextView`; `task` may be `null` and may have a `null` `dueDate`.
- Tests that depend on the current date fix the device clock to the stated `LocalDate` before invoking the binding adapter.

**Post-conditions**
- Success cases
  - An active task due today makes the view visible with text `"D-Day"`. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a visible `TextView` with text `"old"`, and an active `Task` due `2026-10-14` / When `setDueDateLabel` is called / Then the text is `"D-Day"` and visibility is `View.VISIBLE`.
  - An active task due tomorrow makes the view visible with text `"D-1"`. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a hidden `TextView` with text `"old"`, and an active `Task` due `2026-10-15` / When `setDueDateLabel` is called / Then the text is `"D-1"` and visibility is `View.VISIBLE`.
  - An active task overdue by one day makes the view visible with text `"D+1"`. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a hidden `TextView` with text `"old"`, and an active `Task` due `2026-10-13` / When `setDueDateLabel` is called / Then the text is `"D+1"` and visibility is `View.VISIBLE`.
  - A completed task makes the view visible with text `"CLEAR"`, including when no due date exists. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a hidden `TextView` with text `"old"`, and a completed `Task` with `dueDate = null` / When `setDueDateLabel` is called / Then the text is `"CLEAR"` and visibility is `View.VISIBLE`.
- Error cases
  - None. A `null` task represents no label and is handled without throwing. `[Integration]`
    - Tip: Given a visible `TextView` with text `"D-1"` and `task = null` / When `setDueDateLabel` is called / Then the text is `""` and visibility is `View.GONE`.
- Edge cases
  - One day before the overdue/today boundary is displayed as overdue. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a hidden `TextView` with text `"old"`, and an active `Task` due `2026-10-13` / When `setDueDateLabel` is called / Then the text is `"D+1"` and visibility is `View.VISIBLE`.
  - At the overdue/today boundary, the task is displayed as due today. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a hidden `TextView` with text `"old"`, and an active `Task` due `2026-10-14` / When `setDueDateLabel` is called / Then the text is `"D-Day"` and visibility is `View.VISIBLE`.
  - One day after the today/future boundary is displayed as one day remaining. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a hidden `TextView` with text `"old"`, and an active `Task` due `2026-10-15` / When `setDueDateLabel` is called / Then the text is `"D-1"` and visibility is `View.VISIBLE`.
  - An active task with no due date clears existing text and hides the view. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a visible `TextView` with text `"D-3"`, and an active `Task` with `dueDate = null` / When `setDueDateLabel` is called / Then the text is `""` and visibility is `View.GONE`.
  - A `null` task clears existing text and hides the view. `[Integration]`
    - Tip: Given a visible `TextView` with text `"CLEAR"` and `task = null` / When `setDueDateLabel` is called / Then the text is `""` and visibility is `View.GONE`.
  - A completed task with a future due date still displays completion rather than a countdown. `[Integration]`
    - Tip: Given the device clock is `2026-10-14`, a hidden `TextView` with text `"old"`, and a completed `Task` due `2026-10-15` / When `setDueDateLabel` is called / Then the text is `"CLEAR"` and visibility is `View.VISIBLE`.

**Undefined behavior** (optional)
- None.
