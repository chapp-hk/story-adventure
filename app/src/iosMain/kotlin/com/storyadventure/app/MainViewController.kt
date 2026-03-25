package com.storyadventure.app

import UIKit
import ComposeUI
import androidx.compose.runtime.Composable
import com.storyadventure.app.data.GameStorage
import com.storyadventure.app.data.createStorage

class MainViewController: UIViewController {
    override func viewDidLoad() {
        // Initialize persistent storage
        GameStorage.initialize(createStorage())
        
        let view = Application().UIKitApplication {
            StoryAdventureApp()
        }
        let composeView = view as! UIView
        self.view = composeView
    }
}

@Composable
fun StoryAdventureApp() {
    App()
}