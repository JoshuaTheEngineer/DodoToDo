# DodoToDo

![Dodo To-Do Image](resources/graphics/dodo_todo_logo.png)

Based off this LinkedInLearning course [Building an Android App With Architecture Components](https://www.linkedin.com/learning/building-an-android-app-with-architecture-components/)

[Design Patterns](https://www.quora.com/What-are-some-common-design-patterns-in-Android-development)

Concepts:
- Publisher-Subscriber Model
- Model-View-Viewmodel (MVVM)
- Repository Pattern

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

See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v1.0.0-Setup...v2.0.0-AddUI)

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

See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v2.0.0-AddUI...v3.0.0-DataLayer)

1. Add an Entity, DAO, and Database via [Room implementation](https://developer.android.com/reference/androidx/room/package-summary)

    - Since SQLite doesn't support Date as type, you need to create a [type converter](https://developer.android.com/reference/androidx/room/TypeConverters). See **DateConverter.java**

2. Add database tests via [Android JUnit 4](https://developer.android.com/training/testing/junit-runner)

## Step 4: Separating Business and Data

Visit Tag ***v4.0.0-businessLogic*** for substeps 1 to 3. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v3.0.0-DataLayer...v4.0.0-businessLogic)

Visit Tag ***v5.0.0-addEditFeature*** for substeps 4 and 5. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v4.0.0-businessLogic...v5.0.0-addEditFeature)

Visit Tag ***v5.1.0-deleteNoteFeature*** for substep 6. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v5.0.0-addEditFeature...v5.1.0-deleteNoteFeature)

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

6. To delete a note, it's a similar process except you use DAO's deleteNote method

## Step 5: Use onSaveInstanceState for Device orientation immunity

Visit Tag ***v5.2.0-onSavedInstanceState***. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v5.1.0-deleteNoteFeature...v5.2.0-onSavedInstanceState)

1. Use a boolean flag based on 'onSaveInstanceState' so it won't override current note edits. See 'EditorActivity.java'

### Note: Some refactoring
- added recyclerview divider lines. Visit Tag ***v5.2.1-dividerLines***. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v5.2.0-onSavedInstanceState...v5.2.1-dividerLines)
- moved some directories. Visit Tag ***v5.2.2-moveDirectories***. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v5.2.1-dividerLines...v5.2.2-moveDirectories)
- Added a Splash Screen. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/5.2.2-moveDirectories...v5.3.0-splashScreen)

## Step 6: Upgrade Database to include more parameters

Visit Tag ***v6.0.0-migrateDb***. See changes [here](https://github.com/JoshuaTheEngineer/DodoToDo/compare/v5.3.0-splashScreen...v6.0.0-migrateDb)

- For upgrading / updating your Database, use [Migrations](https://developer.android.com/training/data-storage/room/migrating-db-versions)
- After migrating, update your Add / Edit Note feature to save all the new params in note
    - I updated the constraints so that 'note text' can be empty, because some users don't care about the details

### Note: Great Resources for Database migration and Switch UI
- [Android Developers: Migrating DB Versions](https://developer.android.com/training/data-storage/room/migrating-db-versions)
- [Understanding migrations with Room](https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929) by Florina Muntenescu
- [StackOverflow: Adding multiple columns to SQLite table](https://stackoverflow.com/questions/6172815/sqlite-alter-table-add-multiple-columns-in-a-single-statement)
- [StackOverflow: Dealing with nonNull issues](https://stackoverflow.com/questions/52764507/room-how-to-set-fields-notnull-value-is-false)
- [StackOverflow: Migration couldn't handle AND Setting Default values](https://stackoverflow.com/questions/52274366/android-room-migration-didnt-properly-handle-wrong-columns-order)
- [Switch Tutorial with Android Studio](https://abhiandroid.com/ui/switch)
- [StackOverflow: Adding Multiple Views to ScrollView](https://stackoverflow.com/questions/9660328/is-it-impossible-to-add-multiple-views-to-a-scrollview)

## Step 7: Implement Incrementing and Decrementing features for Editor
- I used 'Mutable LiveData' to update the UI for incrementing and decrementing those values.
- There are some rules to keep in mind when implementing these features
    1. The goal is always more than the current number of units
    2. The goal has to be at least one or higher.
    3. The number of units has to be at least zero or higher.