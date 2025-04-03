# Habitizer: Smart Routine Manager

**Contributors:**  
David Sanchez, Emma Chen, Phillip Tran, Joseph Whiteman, Shouki Ibrahim, Jonghun Park

---

## 🔗 Milestone Links

### Milestone 1
- Start Routine Implementation  
- Edit Routine Implementation  

### Milestone 2
- User Stories (BDD Style)  
- Planning Poker Documentation  
- Scenario-Based System Tests  
- Postmortem Analysis  

---

## 📚 Overview

Habitizer is a mobile application designed to help users better manage their time and daily habits through custom routines. The app allows users to create personalized routines, track time spent on tasks with real-time timers, and monitor both total routine duration and individual task durations. Key features include task completion logic, dual time displays, sub-minute tracking, and planned support for persistent data storage.

---

## ✅ Milestone 1 Summary

### Planning & Execution

In our first milestone, our goal was to build the foundation of the app by implementing the ability to start a routine and to edit an existing routine. To stay within scope, we used agile practices and planning poker to estimate our work and determine what would fit within our team’s weekly working capacity.

With a team velocity of 0.6 and 30 total hours planned for the week, our effective capacity was 18 hours. Based on our estimates, we prioritized the following user stories:

- **US 1 – Start Routine** (16 hours)  
- **US 4 – Edit Routine** (2 hours)  

This brought us right to our 18-hour limit.

### Completed Tasks

For the **Start Routine** functionality, we built data structures for Routines and Tasks and wired up the UI to allow a user to launch a routine and begin tracking time. This included creating the layout for the "Routine In Progress" screen, displaying task placeholders, and setting up a live timer that counts up during routine execution. We also ensured that routines without tasks would display a completion message immediately and added JUnit-based unit tests to confirm functionality.

The **Edit Routine** feature added the ability to navigate to an edit screen, where users can view and adjust the tasks for a routine. To prevent data issues, we disabled editing while a routine is running and tested this behavior manually and through edge case validation.

---

## ✅ Milestone 2 Summary

### Velocity and Iteration Reflection

As we continued development, we tracked our team’s actual output to recalculate velocity:

- **Iteration 1:** Estimated 18 hours, worked 24 → Velocity = 0.45  
- **Iteration 2:** Estimated 35 hours, worked 42 → Velocity = 0.375  

This helped us re-scope stories more realistically for future planning.

### User Stories & Features Implemented

During Milestone 2, we expanded Habitizer with multiple user-centered features including:

- Adding custom routines  
- Reordering and deleting tasks  
- Sub-minute task tracking (rounded to 5-second increments)  
- Dual time displays (routine and task)  
- Pause and resume timers  
- Renaming and deleting routines  
- Persistent data (planned with Room)  

We wrote our user stories in BDD format and prioritized features based on estimated impact and effort.

---

## 🧪 System Testing

To ensure robust behavior, we developed scenario-based system tests covering all core flows:

- Creating routines and tasks
- Reordering and deleting tasks
- Pausing/resuming timers and observing correct rounding
- Displaying sub-minute task durations accurately
- Ensuring persistence of changes across app restarts (planned)
- Confirming timers and task states behave correctly when routines are ended manually or automatically

These tests were designed to mimic real user behavior and confirm that each story delivers usable, predictable functionality.

---

## 🧾 Postmortem Reflection

### What Didn't Go Well
- We initially assigned tasks instead of whole user stories, leading to fragmented ownership.
- Merges were sometimes done at too low a level (task vs. feature).
- Testing coverage was delayed in early stages of development.
- Team contributions varied; not all members committed code regularly.

### What Went Well
- We applied agile practices such as planning poker and velocity tracking effectively.
- Code structure followed SOLID principles and MVP architecture.
- Communication was open and productive via Discord.
- Documentation and variable naming were clean and understandable.
- Team reviews helped catch bugs early and ensured good code hygiene.

### Improvements Moving Forward
- Assign stories instead of individual tasks to promote ownership.
- Require tests before pull requests can be merged.
- Encourage balanced participation from all team members.
- Maintain consistent weekly progress updates and check-ins.

---

## 🧰 Tech Stack & Tools

- **Java** & **Android SDK** – Mobile application development  
- **JUnit** – Unit testing  
- **Room (planned)** – Persistent data storage  
- **GitHub Projects** – Project tracking  
- **Figma (planned)** – UI/UX design  
- **Discord** – Team communication and check-ins  

---

## ✅ Conclusion

Habitizer has grown from a simple routine timer into a more thoughtful and structured application for habit-building. Through two development milestones, our team successfully implemented key features such as customizable routines, real-time task tracking, editing flows, and sub-minute time recording. We also gained hands-on experience working in an agile environment, tracking our velocity, using planning poker for estimations, and collaborating through clear code review processes.

While there are still enhancements we’d love to explore — such as UI polish, persistent data storage, and advanced analytics — we’re proud of the foundation we’ve built. Habitizer reflects our team’s ability to plan, execute, and iterate on a meaningful software product while applying industry-relevant tools and practices.

We look forward to applying these lessons in future development work, both in and beyond the classroom.
