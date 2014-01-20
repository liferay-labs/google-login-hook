Google Login Hook
=================

This is a Hook for Liferay that allows users to Sign in using Google account.

The hook is using Google OAuth 2.0 API.

In order to use this Hook you follow this steps:

1. Create a new Web Application using Google Cloud Console (https://cloud.google.com/console)
![Alt text](/add-google-app.jpg "Add Google App")

2. Select OAuth 2.0 Client ID

3. Add the redirect URI that matches your portal settings. It should contain (/c/portal/google_login?cmd=token).
i.e (http://localhost:8080/c/portal/google_login?cmd=token)

When you have done this you need to configure the settings in the Liferay Hook. There are 2 possible ways of doing this (and they both require different information):

1. Copy the Google Client ID and Client Secret selected in the following screenshot:
![Alt text](/configure-google-app_ui.jpg "Configure Google App via UI")

Then, paste this information in Control Panel -> Configuration -> Portal Settings -> Authentication -> Google.

2. Click the Download JSON button to obtain the file client_secrets.json as described in the following screenshot:
![Alt text](/configure-google-app_json.jpg "Configure Google App via JSON")

This file needs to be copied inside the hook war by replacing the file client_secrets.json that is located in com/liferay/google.

First option is more flexible because it allows configuring different settings per portal instance while the second option sets the same configuration for all portal instances.

Then, you're ready to go. Enjoy :)