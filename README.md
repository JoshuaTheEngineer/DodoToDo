# DodoToDo

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

Notes:
- [DataBinding for nested views](https://medium.com/androiddevelopers/android-data-binding-that-include-thing-1c8791dd6038#.lmx38b8gs)