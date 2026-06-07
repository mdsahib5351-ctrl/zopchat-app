# ZopChat Native Android

Ye WebView app nahi hai. Ye native Android Studio Java + Firebase project hai.

## Firebase
- Package name: `com.zopchat`
- `app/google-services.json` already added.
- Firebase project: `zopchat-ts`
- Database style compatible with current web app: `users`, `mobileNumbers`, `chats/{chatId}/messages`.

## Features included
- Mobile number + password login using Firebase Auth synthetic email: `mobile@mobile.zopchat.app`
- New account create
- Profile setup
- Recent chat list
- Search mobile number and start chat
- One-to-one realtime chat
- Online/offline + last seen
- Firestore offline persistence/cache enabled
- Realtime Database presence path also written under `presence/{uid}`
- White/green ZopChat native UI

## Open kaise kare
1. Android Studio open karo.
2. `ZopChatNativeAndroid` folder open karo.
3. Gradle sync hone do.
4. Firebase Console me Authentication > Email/Password enable hona chahiye.
5. Firestore rules me current web app ke rules use karo ya test ke liye temporary authenticated rules use karo.
6. Run button dabao.

## Note
Profile image, Cloudinary upload, status, groups, QR, voice/image message abhi starter version me nahi diya gaya. Ye starter native app chat ke core ke liye hai. Iske upar next version me add ho sakta hai.
