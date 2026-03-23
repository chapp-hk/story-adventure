# Screen Flow — Story Adventure

```
┌─────────────────┐
│   SPLASH        │
│   (logo anim)   │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   ONBOARDING    │ ◄── First launch only
│   - Parent      │
│     verification│
│   - Age select  │
│   - Reading     │
│     level       │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   HOME          │
│   ┌───────────┐ │
│   │ Story     │ │
│   │ Library   │ │
│   │ [Cards]   │ │
│   └───────────┘ │
│                 │
│   [Progress]    │
│   [Settings]    │
└────────┬────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌───────┐ ┌───────┐
│LOCKED │ │SELECTED│
│Story  │ │STORY   │
│Card   │ │DETAIL  │
└───────┘ └────┬────┘
               │
               ▼
        ┌──────────────┐
        │  CHARACTER   │
        │  CREATOR     │
        │  (or select  │
        │   saved)     │
        └──────┬───────┘
               │
               ▼
        ┌──────────────┐
        │   CHAPTER    │
        │   VIEW       │
        │              │
        │  📖 Text +   │
        │  🔊 Audio    │
        │  (auto-play) │
        │              │
        │  [▶ Play]    │
        └──────┬───────┘
               │
        ┌──────┴───────┐
        ▼              ▼
┌──────────────┐ ┌──────────────┐
│   CHOICE     │ │   PUZZLE     │
│   SCREEN     │ │   SCREEN     │
│              │ │              │
│  ○ Option A  │ │  🌲 🍄 🌲 ?  │
│  ○ Option B  │ │              │
│              │ │  [A] [B]     │
│  [Continue]  │ │   [C] [D]    │
└──────┬───────┘ └──────┬───────┘
       │                │
       └────────┬───────┘
                ▼
        ┌──────────────┐
        │   ENDING     │
        │   + MORAL    │
        │              │
        │  ✨ Rewards  │
        │  🌟 earned   │
        │              │
        │  [Replay]    │
        │  [Home]      │
        │  [Share]     │
        └──────────────┘
```

---

## Screen Details

### 1. Home Screen
- Grid of story cards (cover art + title + progress %)
- Filter by: Genre, Length, Reading Level
- Bottom nav: Home | My Stories | Rewards | Settings

### 2. Story Detail
- Synopsis, age range, estimated time
- "Start" / "Continue" button
- Reviews (parent-controlled)

### 3. Chapter View
- Full-screen reading experience
- Tap text to pause/highlight
- Settings gear: font size, audio speed, background music on/off
- Swipe or tap to advance

### 4. Choice Screen
- Two choices displayed as cards with icons
- Subtle hint about theme (e.g., "🤔 Be brave?")

### 5. Puzzle Screen
- Modal overlay
- Animated feedback on correct/incorrect
- Sound effects

### 6. Ending Screen
- Animated celebration
- Moral lesson in kid-friendly language
- Rewards: gems, badges, unlockables

---

## Parent Dashboard (separate entry)

```
┌─────────────────┐
│   PARENT CODE   │ ◄── 4-digit PIN
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   DASHBOARD     │
│                 │
│   📊 Progress   │
│   📖 Books read │
│   🧩 Puzzles    │
│   ⏱️ Time spent │
│                 │
│   ⚙️ Settings   │
│   - Daily limit │
│   - Content    │
│   - Data export│
└─────────────────┘
```

---

## Tech Note

All screens above can be built with **Compose Multiplatform**:
- Shared UI logic in common module
- Platform-specific audio handling (ExoPlayer Android / AVFoundation iOS)
- JSON story loader = instant new content without app updates