# Habitizer: Smart Routine Manager

**Contributors:**  
David Sanchez, Emma Chen, Phillip Tran, Joseph Whiteman, Shouki Ibrahim, Jonghun Park

---

## 🔗 Milestone Links

- **Milestone 1**
  - [Start Routine Implementation](#)
  - [Edit Routine Implementation](#)

- **Milestone 2**
  - [User Stories (BDD Style)](#)
  - [Planning Poker Documentation](#)
  - [Scenario-Based System Tests](#)
  - [Postmortem Analysis](#)

---

## 📚 Overview

**Habitizer** is a task and routine planning app that helps users track task durations, customize routines, and optimize their time management with intuitive features like real-time timers, persistent storage, and dual time tracking.

---

## 🧠 PEAS Definition

| Element      | Description |
|--------------|-------------|
| **Performance** | Routine adherence, task completion, timing accuracy |
| **Environment** | Android app interface, local routine/task data |
| **Actuators** | Tap, checkboxes, start/pause/end UI buttons |
| **Sensors** | User interactions, system time, stored routine/task metadata |

---

## ✅ Milestone 1 Summary

### 🚦 Iteration Planning

- **Velocity:** 0.6  
- **Total Weekly Hours:** 30  
- **Effective Capacity:** 30 × 0.6 = 18 hours

#### 🧮 Planning Poker Estimates

| User Story | Description              | Hours |
|------------|--------------------------|--------|
| US 1       | Start Routine            | 16     |
| US 2       | Complete Task            | 8      |
| US 3       | End Routine              | 4      |
| US 4       | Edit Routine             | 2      |
| US 5       | Edit Task                | 8      |
| US 6       | Add Task                 | 8      |
| US 7       | Set Routine Time         | 4      |

#### ✅ Iteration 1 Scope (18 Hours)
- US 1 – Start Routine (16h)
- US 4 – Edit Routine (2h)

---

### 🧪 Iteration 1 Tasks

#### US 1 – Start Routine (16h)

| Task | Description | Est. |
|------|-------------|------|
| Create Data Structures | Routine & Task classes with name, task list, expected time | 2h |
| Main Screen & Button | UI Button to Start Routine, link to in-progress screen | 2h |
| RoutineInProgress Screen | XML layout for timer + task list | 3h |
| Timer Logic | CountUp Timer (Handler/Runnable) with live display | 3h |
| Link Routine Data | Pass data from main to routine screen | 3h |
| Testing & Fixes | Edge cases (empty routine), JUnit tests | 3h |

#### US 4 – Edit Routine (2h)

| Task | Description | Est. |
|------|-------------|------|
| Edit Button & Screen | Show task list in edit view | 1h |
| Disable During Routine | Prevent edits while active | 0.5h |
| Testing | Ensure edit logic behaves correctly | 0.5h |

---

## ✅ Milestone 2 Summary

### 🔁 Velocity Calculations

| Iteration | Estimated Hours | Worked Hours | Velocity |
|-----------|------------------|---------------|----------|
| Iteration 1 | 18 | 24 | 0.45 |
| Iteration 2 | 35 | 42 | 0.375 |

---

### 📘 Milestone 2 User Stories (BDD + Estimates)

| Story # | Description                         | Est. |
|---------|-------------------------------------|------|
| 11      | Add Custom Routines                 | 4h   |
| 12      | Reorder Tasks in a Routine          | 8h   |
| 13      | Delete A Task                       | 4h   |
| 14      | Continuous Count-Up Timer           | 2h   |
| 15      | Pause and Resume Routine            | 2h   |
| 16      | Sub-Minute Task Times in 5s         | 2h   |
| 17      | Dual Time Display                   | 2h   |
| 18      | Rename Routine                      | 2h   |
| 19      | Delete a Routine                    | 4h   |
| 20      | Persistence of Data                 | 16h  |

---

### 🧪 Scenario-Based System Tests

Tests simulate real user flows from task creation to routine execution. They include:

- Creating, reordering, and deleting tasks
- Sub-minute and dual time tracking
- Persisting changes after app relaunch
- Timer rounding, pause/resume logic

> ✅ See: `Scenario-Based System Test Table` (Jonghun)

---

### 🧾 Postmortem Reflection

#### ❌ What Didn’t Go Well
- Assigned work by task, not story
- Merged branches too frequently at low-level
- Insufficient test coverage early on
- Some team members didn’t contribute commits equally

#### ✅ What Went Well
- Strong adherence to SOLID principles
- Consistent commit and PR structure
- Open communication through Discord
- Well-documented logic and reusable architecture

#### 🧩 Risk Mitigation for Future Milestones
- Use **story-based branching**
- Require test completion before merge
- Assign full stories to pairs or individuals
- Weekly check-ins on code contributions

---

## 📦 Tech Stack & Tools

- **Java + Android SDK** – Mobile app development
- **JUnit** – Unit testing framework
- **GitHub Projects + Branching Strategy** – Task management
- **Room (Planned)** – Persistent data layer
- **Figma (Planned)** – UI prototyping and user flow planning

---

## 🗓️ Looking Ahead to Milestone 3

- Polish UI: Animations, better list visuals
- Add Firebase or local JSON backup
- Weekly stats (time spent, most completed tasks)
- Improve scoring logic for task efficiency
