# DodoToDo

Based off this [LinkedInLearning](https://www.linkedin.com/learning/building-an-android-app-with-architecture-components/)

## Step 1: Setup

View Tag ***v1.0.0-Setup***

1. Add a Vector Asset based on Android's preexisting Add, Edit, Delete, and Check icons 0. To allow custom assets, - go to build.gradle(app) - set this 'vectorDrawables.useSupportLibrary = true' under 'defaultConfig'
    i. Right Click the app directory and choose 'Create New Vector Asset*
    ii. Select 'Clip Art' and search for the 'Add button'
    iii. I made it #FFFFF or white and renamed it as the ic_add.xml
    iv. it will be in the drawable directory
    v. Repeat steps 1 to 4 with the remaining buttons

2. Add [LifeCycle and Room dependencies](https://developer.android.com/topic/libraries/architecture/adding-components). This will include support for:
    - LiveData
    - ViewModel

## Step 2: Notes List UI

Visit Tag ***v2.0.0-AddUI***

1. Add a RecyclerView under [DataBinding](https://developer.android.com/topic/libraries/data-binding)

    - Optional. Use ButterKnife. In "File > Project Structure > Dependencies > App", click the add button to include latest versions of
        i. jakewharton:butterknife
        ii. jakewharton:butterknife-compiler (added as annotationProcessor)

2. Create a custom note item view. See **note_list_item.xml**

3. Create a data transfer object with proper Getters, Setters, and Constructors. See **NoteEntity.java** as an example

    - To test it, we need to create Sample Data. You can log them in console to see if they work. See 'SampleData.java'

4. Create a Notes [Adapter](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter) that determines UI for each note in the recyclerview

5. Add an Editor Activity, I used a Scrolling Activity to be later used for editing notes

### Notes:
- [DataBinding for nested views](https://medium.com/androiddevelopers/android-data-binding-that-include-thing-1c8791dd6038#.lmx38b8gs)
    - Additional Helpful notes from [StackOverflow](https://stackoverflow.com/questions/34636934/android-data-binding-setsupportactionbar)

## Step 3: Data Layer

Visit Tag ***v3.0.0-DataLayer***

1. Add an Entity, DAO, and Database via [Room implementation](https://developer.android.com/reference/androidx/room/package-summary)

    - Since SQLite doesn't support Date as type, you need to create a [type converter](https://developer.android.com/reference/androidx/room/TypeConverters). See **DateConverter.java**

2. Add database tests via [Android JUnit 4](https://developer.android.com/training/testing/junit-runner)

## Step 4: Separating Business and Data

1. Add a [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

2. Add a Repository which help will direct where data comes from.
    - MainViewModel will use AppRepository
    - MainActivity uses MainViewModel
    - I heard you can view  SQL Lite data with ['DB Browser for SQLite'](https://sqlitebrowser.org/), but I wasn't able to get it working

3. Via this relationship, Activity -> ViewModel -> Repository
    - it can add sample data
    - delete all notes

4. Using Bundle and each note item's FAB btn to configure saving each Edit note.

5. Use above configuration with Bundle and Fab button to add a note.