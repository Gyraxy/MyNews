# MyNews [![Build Status](https://travis-ci.com/Gyraxy/MyNews.svg?branch=dev)](https://travis-ci.com/Gyraxy/MyNews)

My News is an Android Application which get the news from the New York Times API. It shows last articles uploaded on New York Times and can be read via a Web View.

## Specifications
Compliant with all Android mobile phone in portrait view.
Minimum Android 4.4 required.

## How to use

Clone or download the folder to your desktop and launch it via Android Studio.

## Functionnalities

In this application you can read articles from different type of API shown in 4 differents tabs :
1. **TopStories** : Last articles get from the TopStories NewYorkTimes API;
2. **MostPopular** : Last articles get from the Popular NewYorkTimes API;
3. **Weekly** : Last weekly articles get from the Search NewYorkTimes API. This tab is empty when the application is launched for the first time. To know how to get weekly articles see below;
4.  **Search** : A selection of articles search in Search NewYorkTimes API with term query, section, begin and end date. This tab is empty when the application is launched for the first time. To know how to get search articles see below;

### Read Weekly Articles:
To show weekly articles you need to select a section. The sections available are listed in the navigation drawer. When you select the section, the list of weekly articles are shown in the weekly tabs. 
The section selected is automatically saved and configured. If you reboot the application then the previous section selected is shown. If you wan to change the section then you need to click on a new section in the navigation drawer.

### Search Articles:
You want to find specific articles then click on the loop icon. You will be on the Search Article Window. At least one query term and section is needed before clicking on the search button.
If there are articles according to your criteria then a list will be shown. Otherwise a popup is shown with "No Articles found".
The articles found according to your search are saved and shown in the Search tab.

### Notification:
If you don't want to miss any new article today then click on the notification button. You can schedule notification with one term querry and one section. By activating the switch notification will be scheduled every day at 9am.
You will receive a notification with the results. This notification can be clicked to show Notification results. If you want to read again these articles then click on the ring icon on the first application window.

## Last Modification
18/09/2018 Application first released.

## Licence
```
Copyright (C) 2018 Gyraxy (DUBOSCQ Nicolas)
```