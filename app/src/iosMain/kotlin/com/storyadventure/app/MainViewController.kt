package com.storyadventure.app

import UIKit
import ComposeUI
import androidx.compose.runtime.Composable

class MainViewController: UIViewController {
    override func viewDidLoad() {
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