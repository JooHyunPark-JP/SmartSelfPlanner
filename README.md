# SmartSelfPlanner

Language: Kotlin.

Library and tech being used:

MVVM(Model-View-ViewModel), Coroutine, Room database, SQLite, Notification, Broadcast Service, Pending Intent, Data binding, Recyclerview, LiveData, App Navigation(Fragments), Menu manager, Timer… and more. 

## About the app.
So far, a simple To-do page and daily task page are completed.

## Project Overview.

1. Main Page:
The main page is where the user can monitoring the overall condition (eg: How many tasks are done) from each planner. 
<img src="/images/UserMainPageTrim.jpg"/>

2. To-do page:
A user where make To-do lists. Multiple select is available.

3. Daily Tasks:
Where users make tasks that should do on a daily basis. Users can set a timer (which is optional) for each task (For example: Read a book for 30 mins.) 

<img src="/images/DailyTaskMain.jpg"/>

When the timer is finished, the app will alert the phone and send a notification (Timer and notification are working in the background therefore even if the app is closed, it will still work) 

<img src="/images/notification.jpg"/>

All the planners can be modified by users. (Add, delete and edit)



## Future work

- Change to modern UI
- Implement cool animation for the effects (animation when move to other frames(activity), click button, row for each plans)
- Create Workout planner. (Method: Not determined yet)
- Create caladner for monthly planner.
- Make an app able to share user's planner to facebook, Twitter... etc


