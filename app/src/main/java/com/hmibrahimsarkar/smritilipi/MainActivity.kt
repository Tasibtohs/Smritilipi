package com.hmibrahimsarkar.smritilipi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.hmibrahimsarkar.smritilipi.ui.components.NavigationDrawerContent
import com.hmibrahimsarkar.smritilipi.ui.screens.AboutScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.BackupRestoreScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.EditorScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.GroupsScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.HiddenNotesScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.NotesListScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.PrivacyPolicyScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.SettingsScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.SplashScreen
import com.hmibrahimsarkar.smritilipi.ui.screens.TrashScreen
import com.hmibrahimsarkar.smritilipi.ui.theme.SmritilipiTheme
import com.hmibrahimsarkar.smritilipi.ui.viewmodel.MainViewModel
import com.hmibrahimsarkar.smritilipi.ui.viewmodel.Screen
import kotlinx.coroutines.launch

import com.hmibrahimsarkar.smritilipi.ui.font.BengaliFonts

import androidx.compose.material3.ExperimentalMaterial3Api

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BengaliFonts.init(this)
        enableEdgeToEdge()

        handleNotificationIntent(intent)

        setContent {
            val isDarkModePref by mainViewModel.isDarkMode.collectAsState()
            val fontSizePref by mainViewModel.fontSizePreference.collectAsState()
            val selectedThemeId by mainViewModel.selectedThemeId.collectAsState()

            SmritilipiTheme(
                themeId = selectedThemeId,
                isDarkMode = isDarkModePref ?: true,
                fontSizePreference = fontSizePref
            ) {
                MainAppContent(
                    viewModel = mainViewModel,
                    isDarkMode = isDarkModePref
                )
            }
        }
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: android.content.Intent?) {
        if (intent?.getBooleanExtra("OPEN_NEW_NOTE", false) == true) {
            mainViewModel.navigateTo(com.hmibrahimsarkar.smritilipi.ui.viewmodel.Screen.Editor(null))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContent(
    viewModel: MainViewModel,
    isDarkMode: Boolean?
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val currentScreen by viewModel.currentScreen.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val effectiveDark = isDarkMode ?: true

    val exportAllPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf")
    ) { uri ->
        if (uri != null) {
            viewModel.exportAllNotesToPdfToUri(context, uri)
        }
    }

    // Back button handling
    BackHandler(enabled = currentScreen !is Screen.NotesList && currentScreen !is Screen.Splash) {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            viewModel.navigateTo(Screen.NotesList)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = currentScreen !is Screen.Editor && currentScreen !is Screen.Splash,
        drawerContent = {
            NavigationDrawerContent(
                currentScreen = currentScreen,
                isDarkMode = effectiveDark,
                onNavigate = { targetScreen ->
                    viewModel.navigateTo(targetScreen)
                },
                onToggleDarkMode = { isDark ->
                    viewModel.toggleDarkMode(isDark)
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                },
                onExportAllPdf = {
                    val dateStr = SimpleDateFormat("dd_MMM_yyyy", Locale("bn", "BD")).format(Date())
                    exportAllPdfLauncher.launch("Smritilipi_সংকলন_$dateStr.pdf")
                }
            )
        }
    ) {
        AnimatedContent(
            targetState = currentScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            modifier = Modifier.fillMaxSize(),
            label = "ScreenTransition"
        ) { screen ->
            when (screen) {
                is Screen.Splash -> {
                    SplashScreen(
                        onSplashFinished = {
                            viewModel.navigateTo(Screen.NotesList)
                        }
                    )
                }

                is Screen.NotesList, is Screen.PinnedNotes -> {
                        NotesListScreen(
                            viewModel = viewModel,
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onOpenEditor = { noteId ->
                                viewModel.navigateTo(Screen.Editor(noteId))
                            }
                        )
                    }

                    is Screen.Editor -> {
                        EditorScreen(
                            noteId = screen.noteId,
                            mainViewModel = viewModel,
                            onBack = {
                                viewModel.navigateTo(Screen.NotesList)
                            }
                        )
                    }

                    is Screen.Groups -> {
                        GroupsScreen(
                            viewModel = viewModel,
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onBack = { viewModel.navigateTo(Screen.NotesList) },
                            onOpenEditor = { noteId ->
                                viewModel.navigateTo(Screen.Editor(noteId))
                            }
                        )
                    }

                    is Screen.HiddenNotes -> {
                        HiddenNotesScreen(
                            viewModel = viewModel,
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onBack = { viewModel.navigateTo(Screen.NotesList) },
                            onOpenEditor = { noteId ->
                                viewModel.navigateTo(Screen.Editor(noteId))
                            }
                        )
                    }

                    is Screen.Trash -> {
                        TrashScreen(
                            viewModel = viewModel,
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onBack = { viewModel.navigateTo(Screen.NotesList) }
                        )
                    }

                    is Screen.BackupRestore -> {
                        BackupRestoreScreen(
                            viewModel = viewModel,
                            onOpenDrawer = { scope.launch { drawerState.open() } },
                            onBack = { viewModel.navigateTo(Screen.NotesList) }
                        )
                    }

                    is Screen.Settings -> {
                        SettingsScreen(
                            viewModel = viewModel,
                            onBack = { viewModel.navigateTo(Screen.NotesList) }
                        )
                    }

                    is Screen.About -> {
                        AboutScreen(
                            onBack = { viewModel.navigateTo(Screen.NotesList) }
                        )
                    }

                    is Screen.PrivacyPolicy -> {
                        PrivacyPolicyScreen(
                            onBack = { viewModel.navigateTo(Screen.NotesList) }
                        )
                    }
                }
            }
        }
    }
