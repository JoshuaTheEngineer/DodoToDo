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

1. Add a RecyclerView under [DataBinding](https://developer.android.com/topic/libraries/data-binding)

    - Optional. Use ButterKnife. In "File > Project Structure > Dependencies > App", click the add button to include latest versions of
        i. jakewharton:butterknife
        ii. jakewharton:butterknife-compiler (added as annotationProcessor)

2. Create a custom note item view. See **note_list_item.xml**

3. Create a data transfer object with propper Getters, Setters, and Constructors. See **NoteEntity.java** as an example

    - To test it, we need to create Sample Data. You can log them in console to see if they work. See 'SampleData.java'

4. Create a Notes [Adapter](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter) that determines UI for each note in the recyclerview

5. Add an Editor Activity, I used a Scrolling Activity to be later used for editing notes

Notes:
- [DataBinding for nested views](https://medium.com/androiddevelopers/android-data-binding-that-include-thing-1c8791dd6038#.lmx38b8gs)