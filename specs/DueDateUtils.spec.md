# Spec: `DueDateUtils.kt`

> Post-conditions are the test oracle: write the expected behavior from intent, not from the code.
> Tag each post-condition [Unit] if its test runs on the JVM; otherwise tag it [Integration].
> End each valid case with `Tested by <test>` if an existing test checks it, or `Tip:` describing the test to write.

## `internal fun dueDateLabel(dueDate: LocalDate?, today: LocalDate, isCompleted: Boolean): String`

**Intent**: Produce the compact D-day label for a task-list row. Completion takes precedence; otherwise it shows no due date, today, remaining days, or overdue days.

**Pre-conditions**
- `today` is a valid `LocalDate`; `dueDate` may be `null`.

**Post-conditions**
- Success cases
  - A completed task returns `"CLEAR"` even with no due date. `[Unit]`
    - Tip: Given `dueDate = null`, `today = 2026-10-14`, and `isCompleted = true` / When `dueDateLabel` is called / Then it returns `"CLEAR"`.
  - A completed task returns `"CLEAR"` even with a future due date. `[Unit]`
    - Tip: Given `dueDate = 2026-10-15`, `today = 2026-10-14`, and `isCompleted = true` / When `dueDateLabel` is called / Then it returns `"CLEAR"`.
  - An active task with no due date returns the empty string. `[Unit]`
    - Tip: Given `dueDate = null`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `""`.
  - An active task due on `today` returns `"D-Day"`. `[Unit]`
    - Tip: Given `dueDate = 2026-10-14`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-Day"`.
  - An active task due after `today` returns `"D-N"` for the positive number of calendar days remaining. `[Unit]`
    - Tip: Given `dueDate = 2026-10-17`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-3"`.
  - An active task due before `today` returns `"D+N"` for the positive number of calendar days overdue. `[Unit]`
    - Tip: Given `dueDate = 2026-10-11`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D+3"`.
- Error cases
  - None. Every valid `LocalDate`, including dates across months, years, and leap days, and a `null` due date are accepted. `[Unit]`
    - Tip: Given `dueDate = 2024-02-29`, `today = 2024-02-28`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-1"`.
- Edge cases
  - One day before the overdue/today boundary returns `"D+1"`. `[Unit]`
    - Tip: Given `dueDate = 2026-10-13`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D+1"`.
  - At the overdue/today boundary, the label returns `"D-Day"`. `[Unit]`
    - Tip: Given `dueDate = 2026-10-14`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-Day"`.
  - One day after the today/future boundary returns `"D-1"`. `[Unit]`
    - Tip: Given `dueDate = 2026-10-15`, `today = 2026-10-14`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-1"`.
  - Adjacent dates across a month end remain one calendar day apart. `[Unit]`
    - Tip: Given `dueDate = 2026-11-01`, `today = 2026-10-31`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-1"`.
  - Adjacent dates across a year end remain one calendar day apart. `[Unit]`
    - Tip: Given `dueDate = 2027-01-01`, `today = 2026-12-31`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-1"`.
  - The day before leap day is one calendar day before it. `[Unit]`
    - Tip: Given `dueDate = 2024-02-29`, `today = 2024-02-28`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-1"`.
  - Leap day itself is due today. `[Unit]`
    - Tip: Given `dueDate = 2024-02-29`, `today = 2024-02-29`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-Day"`.
  - The day after leap day is one calendar day after it. `[Unit]`
    - Tip: Given `dueDate = 2024-03-01`, `today = 2024-02-29`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-1"`.
  - A future due date across a month boundary uses the total calendar-day difference. `[Unit]`
    - Tip: Given `dueDate = 2026-11-03`, `today = 2026-10-01`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D-33"`.
  - A past due date across a month boundary uses the total calendar-day difference. `[Unit]`
    - Tip: Given `dueDate = 2026-10-01`, `today = 2026-11-03`, and `isCompleted = false` / When `dueDateLabel` is called / Then it returns `"D+33"`.

**Undefined behavior** (optional)
- None.
