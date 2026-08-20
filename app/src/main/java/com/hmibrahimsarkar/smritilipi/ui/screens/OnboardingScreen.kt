package com.hmibrahimsarkar.smritilipi.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmibrahimsarkar.smritilipi.ui.theme.AppTitleFont
import com.hmibrahimsarkar.smritilipi.ui.theme.GoldDark
import com.hmibrahimsarkar.smritilipi.ui.theme.GoldLight
import com.hmibrahimsarkar.smritilipi.ui.theme.GoldPrimary
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

data class OnboardingFeatureBullet(
    val icon: ImageVector,
    val title: String,
    val caption: String
)

data class OnboardingSlideData(
    val stepNumber: String,
    val badgeLabel: String,
    val title: String,
    val subtitleTagline: String,
    val description: String,
    val primaryIcon: ImageVector,
    val decorativeGlyphs: List<String>,
    val highlights: List<OnboardingFeatureBullet>,
    val accentColor: Color,
    val secondaryAccent: Color
)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val slides = listOf(
        OnboardingSlideData(
            stepNumber = "০১",
            badgeLabel = "অভিজ্ঞতা ০১ • ডিজিটাল ডায়েরি",
            title = "স্মৃতিলিপিতে স্বাগতম",
            subtitleTagline = "আপনার স্মৃতি ও অনুভূতির নান্দনিক ডিজিটাল ঠিকানা",
            description = "কবিতা, ছোট গল্প, দিনলিপি কিংবা মনের গভীর অনুভূতি—সবকিছু নিরাপদে ও চমৎকারভাবে গুছিয়ে রাখার এক নির্ভরযোগ্য ও নান্দনিক ডায়েরি।",
            primaryIcon = Icons.Default.MenuBook,
            decorativeGlyphs = listOf("স্মৃতি", "লিপি", "কাব্য"),
            highlights = listOf(
                OnboardingFeatureBullet(
                    icon = Icons.Default.WorkspacePremium,
                    title = "১০০% অফলাইন ও নিরাপদ",
                    caption = "আপনার ডেটা আপনার ডিভাইসেই সুরক্ষিত থাকে"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.AutoStories,
                    title = "বিজ্ঞাপনমুক্ত ডায়েরি",
                    caption = "কোনো বিভ্রান্তি ছাড়া নিরবচ্ছিন্ন লেখার পরিবেশ"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Palette,
                    title = "রয়্যাল মেটেরিয়াল ৩ থিম",
                    caption = "ডার্ক ও লাইট দুই মোডেই রাজকীয় ভিজ্যুয়াল লুক"
                )
            ),
            accentColor = GoldPrimary,
            secondaryAccent = Color(0xFFEAB308)
        ),
        OnboardingSlideData(
            stepNumber = "০২",
            badgeLabel = "অভিজ্ঞতা ০২ • সৃষ্টিশীলতা",
            title = "লিখুন ও সাজিয়ে রাখুন",
            subtitleTagline = "বাংলা টাইপোগ্রাফি ও রঙিন পেপারের অপূর্ব মেলবন্ধন",
            description = "+ বাটনে ট্যাপ করে নিমেষেই নতুন লেখা শুরু করুন। ১০টিরও বেশি আকর্ষণীয় বাংলা ফন্ট, টেক্সট কালার, ব্যাকগ্রাউন্ড পেপার ও সাইজ কাস্টমাইজ করুন।",
            primaryIcon = Icons.Default.Create,
            decorativeGlyphs = listOf("অ", "আ", "ক"),
            highlights = listOf(
                OnboardingFeatureBullet(
                    icon = Icons.Default.TextFields,
                    title = "১০+ ইউনিক বাংলা ফন্ট",
                    caption = "অনুপম, হিন্দ শিলিগুড়ি, সলিমুল্লাহ ও চারুলিপি ইত্যাদি"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Draw,
                    title = "নান্দনিক পেপার ও কালার",
                    caption = "চোখের আরামের জন্য পার্চমেন্ট, চারকোল ও ভেলভেট থিম"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Check,
                    title = "রিয়েল-টাইম অটো সেভ",
                    caption = "প্রতিটি অক্ষর সাথে সাথে স্বয়ংক্রিয়ভাবে সংরক্ষিত হয়"
                )
            ),
            accentColor = Color(0xFFF59E0B),
            secondaryAccent = Color(0xFFFFB74D)
        ),
        OnboardingSlideData(
            stepNumber = "০৩",
            badgeLabel = "অভিজ্ঞতা ০৩ • সর্বোচ্চ নিরাপত্তা",
            title = "নিরাপদ ও লক সুরক্ষিত",
            subtitleTagline = "ব্যক্তিগত গোপনীয়তায় কোনো আপস নেই",
            description = "গুরুত্বপূর্ণ লেখাগুলো এক ট্যাপে লক করে ভুলবশত মুছে ফেলা বা পরিবর্তন থেকে বাঁচান। আর অতি গোপন লেখাগুলো রাখুন পাসওয়ার্ড সুরক্ষিত 'হাইডেন নোটস'-এ।",
            primaryIcon = Icons.Default.Security,
            decorativeGlyphs = listOf("🔒", "🛡️", "🔑"),
            highlights = listOf(
                OnboardingFeatureBullet(
                    icon = Icons.Default.Lock,
                    title = "নোট লক প্রতিরোধ",
                    caption = "লক থাকা অবস্থায় লেখা অনাকাঙ্ক্ষিতভাবে এডিট বা ডিলিট হবে না"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Security,
                    title = "গোপন হাইডেন নোটস",
                    caption = "মাস্টার পাসওয়ার্ড ছাড়া মূল তালিকায় এই নোটগুলো দেখাই যাবে না"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Bookmark,
                    title = "সিকিউরিটি রিকভারি",
                    caption = "পাসওয়ার্ড ভুলে গেলে সিকিউরিটি প্রশ্নোত্তরের মাধ্যমে রিকভার করুন"
                )
            ),
            accentColor = Color(0xFF10B981),
            secondaryAccent = Color(0xFF34D399)
        ),
        OnboardingSlideData(
            stepNumber = "০৪",
            badgeLabel = "অভিজ্ঞতা ০৪ • সুবিন্যাস",
            title = "গুছিয়ে রাখুন ফোল্ডারে",
            subtitleTagline = "কাস্টম গ্রুপ, শীর্ষে পিন ও স্মার্ট ফিল্টার",
            description = "কবিতা, গল্প, শপিং বা ডায়েরির জন্য তৈরি করুন আলাদা গ্রুপ। অতি প্রয়োজনীয় লেখাগুলোকে উপরে পিন করুন এবং মাল্টি-সিলেক্ট দিয়ে দ্রুত ম্যানেজ করুন।",
            primaryIcon = Icons.Default.FolderSpecial,
            decorativeGlyphs = listOf("📁", "📌", "🔍"),
            highlights = listOf(
                OnboardingFeatureBullet(
                    icon = Icons.Default.FolderSpecial,
                    title = "কাস্টম গ্রুপ ও ক্যাটাগরি",
                    caption = "রঙিন আইকন সহ যত ইচ্ছা ক্যাটাগরি ফোল্ডার তৈরি করুন"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.PushPin,
                    title = "শীর্ষে পিন (Pin Note)",
                    caption = "প্রিয় নোটগুলোকে এক ক্লিকে তালিকার একদম উপরে ধরে রাখুন"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Search,
                    title = "দ্রুত লাইভ সার্চ",
                    caption = "যেকোনো শব্দ বা বাক্য লিখলেই তাৎক্ষণিক নোট সামনে চলে আসবে"
                )
            ),
            accentColor = Color(0xFF6366F1),
            secondaryAccent = Color(0xFF818CF8)
        ),
        OnboardingSlideData(
            stepNumber = "০৫",
            badgeLabel = "অভিজ্ঞতা ০৫ • এক্সপোর্ট ও শেয়ার",
            title = "রয়্যাল PDF ও শেয়ার",
            subtitleTagline = "ডিজিটাল স্বাক্ষর ও প্রিন্ট-রেডি ফরম্যাট",
            description = "আপনার লেখাগুলো একটি নান্দনিক PDF ডকুমেন্টে রূপান্তর করে প্রিন্ট করুন বা বন্ধুদের সাথে শেয়ার করুন। সাথে যুক্ত করতে পারবেন আপনার ব্যক্তিগত লেখক স্বাক্ষর!",
            primaryIcon = Icons.Default.PictureAsPdf,
            decorativeGlyphs = listOf("📄", "✒️", "🚀"),
            highlights = listOf(
                OnboardingFeatureBullet(
                    icon = Icons.Default.PictureAsPdf,
                    title = "প্রিন্ট-রেডি প্রিমিয়াম PDF",
                    caption = "পছন্দের ফন্ট ও পেপার স্টাইল অক্ষুণ্ণ রেখে PDF তৈরি করুন"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Draw,
                    title = "কাস্টম লেখক স্বাক্ষর",
                    caption = "ডকুমেন্টের নিচে যুক্ত হবে 'ইতি - আপনার নাম' ব্র্যান্ডিং"
                ),
                OnboardingFeatureBullet(
                    icon = Icons.Default.Share,
                    title = "এক ক্লিকে সোশ্যাল শেয়ার",
                    caption = "মেসেঞ্জার, ফেসবুক বা হোয়াটসঅ্যাপে সরাসরি টেক্সট কপি বা শেয়ার"
                )
            ),
            accentColor = Color(0xFFEC4899),
            secondaryAccent = Color(0xFFF472B6)
        )
    )

    val pagerState = rememberPagerState(initialPage = 0) { slides.size }
    val isLastPage = pagerState.currentPage == slides.size - 1

    // Handle system back button
    BackHandler {
        if (pagerState.currentPage > 0) {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        } else {
            onFinish()
        }
    }

    // Infinite breathing glow animation
    val infiniteTransition = rememberInfiniteTransition(label = "ambientGlow")
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val currentSlide = slides[pagerState.currentPage]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // ================= ATMOSPHERIC LUXURY GLOW CANVAS =================
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            // Top-Right Ambient Orb
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        currentSlide.accentColor.copy(alpha = 0.16f),
                        currentSlide.accentColor.copy(alpha = 0.05f),
                        Color.Transparent
                    ),
                    center = Offset(canvasW * 0.85f, canvasH * 0.15f),
                    radius = (canvasW * 0.6f) * glowPulse
                ),
                radius = (canvasW * 0.6f) * glowPulse,
                center = Offset(canvasW * 0.85f, canvasH * 0.15f)
            )

            // Bottom-Left Ambient Orb
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        currentSlide.secondaryAccent.copy(alpha = 0.14f),
                        currentSlide.secondaryAccent.copy(alpha = 0.04f),
                        Color.Transparent
                    ),
                    center = Offset(canvasW * 0.15f, canvasH * 0.75f),
                    radius = (canvasW * 0.65f) * glowPulse
                ),
                radius = (canvasW * 0.65f) * glowPulse,
                center = Offset(canvasW * 0.15f, canvasH * 0.75f)
            )

            // Subtle top decorative gold line
            drawLine(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.Transparent,
                        GoldPrimary.copy(alpha = 0.35f),
                        GoldLight.copy(alpha = 0.7f),
                        GoldPrimary.copy(alpha = 0.35f),
                        Color.Transparent
                    )
                ),
                start = Offset(canvasW * 0.1f, 0f),
                end = Offset(canvasW * 0.9f, 0f),
                strokeWidth = 2.dp.toPx()
            )
        }

        // ================= MAIN CONTENT HIERARCHY =================
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // ================= TOP BAR (Skip, Step Counter, Back) =================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Button (If not on first page)
                if (pagerState.currentPage > 0) {
                    Surface(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
                        border = BorderStroke(1.dp, currentSlide.accentColor.copy(alpha = 0.3f)),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Previous",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.size(38.dp))
                }

                // Step Pill with Metallic Gradient Border
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75f),
                    border = BorderStroke(
                        1.2.dp,
                        Brush.horizontalGradient(
                            listOf(GoldLight.copy(alpha = 0.8f), GoldPrimary, GoldDark.copy(alpha = 0.5f))
                        )
                    ),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(GoldPrimary)
                        )
                        Text(
                            text = "ধাপ ${currentSlide.stepNumber} / ০৫",
                            color = GoldPrimary,
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = AppTitleFont
                        )
                    }
                }

                // Skip Button
                Surface(
                    onClick = onFinish,
                    shape = RoundedCornerShape(14.dp),
                    color = if (isLastPage) GoldPrimary.copy(alpha = 0.15f) else Color.Transparent,
                    border = if (isLastPage) BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)) else null
                ) {
                    Text(
                        text = if (isLastPage) "সম্পন্ন" else "এড়িয়ে যান",
                        color = if (isLastPage) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 13.5.sp,
                        fontWeight = if (isLastPage) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            // ================= HORIZONTAL PAGER =================
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                val slide = slides[page]
                OnboardingSlideItem(slide = slide)
            }

            // ================= BOTTOM CONTROLS (Pill Indicators & Action Button) =================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Interactive Expandable Indicator Pills
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in slides.indices) {
                        val isSelected = pagerState.currentPage == i
                        val width by animateDpAsState(
                            targetValue = if (isSelected) 34.dp else 9.dp,
                            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
                            label = "pillWidth"
                        )
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(width)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (isSelected) {
                                        Brush.horizontalGradient(
                                            listOf(GoldLight, GoldPrimary, GoldDark)
                                        )
                                    } else {
                                        Brush.linearGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                                                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                                            )
                                        )
                                    }
                                )
                                .clickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(i)
                                    }
                                }
                        )
                    }
                }

                // Action Buttons
                if (isLastPage) {
                    // Final "Get Started" Gold Metallic Gradient Button
                    Button(
                        onClick = onFinish,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .shadow(10.dp, RoundedCornerShape(18.dp), spotColor = GoldPrimary),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFF1B1100)
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(GoldLight, GoldPrimary, GoldDark)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "স্মৃতিলিপিতে শুরু করুন",
                                    fontSize = 16.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppTitleFont,
                                    color = Color(0xFF1A1100)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFF1A1100).copy(alpha = 0.15f),
                                    modifier = Modifier.size(26.dp)
                                ) {
                                Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Start",
                                            tint = Color(0xFF1A1100),
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (pagerState.currentPage > 0) {
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp),
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(
                                    1.dp,
                                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)
                                )
                            ) {
                                Text(
                                    text = "পূর্ববর্তী",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.5.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            modifier = Modifier
                                .weight(if (pagerState.currentPage > 0) 1.6f else 1f)
                                .height(52.dp)
                                .shadow(6.dp, RoundedCornerShape(16.dp), spotColor = GoldPrimary.copy(alpha = 0.4f)),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = Color(0xFF1E1400)
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "পরবর্তী",
                                    fontSize = 15.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = AppTitleFont
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "Next",
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingSlideItem(slide: OnboardingSlideData) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        // ================= 3D-STYLED HERO STAGE VISUAL =================
        SlideHeroVisualStage(slide = slide)

        Spacer(modifier = Modifier.height(18.dp))

        // ================= BADGE & TITLES =================
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = slide.accentColor.copy(alpha = 0.12f),
            border = BorderStroke(1.dp, slide.accentColor.copy(alpha = 0.35f))
        ) {
            Text(
                text = slide.badgeLabel,
                color = slide.accentColor,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = slide.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = AppTitleFont,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = slide.subtitleTagline,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = GoldPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = slide.description,
            fontSize = 13.5.sp,
            lineHeight = 20.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        // ================= FEATURE MATRIX HIGHLIGHT CARDS =================
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            slide.highlights.forEach { bullet ->
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Icon Pill
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = slide.accentColor.copy(alpha = 0.16f),
                            border = BorderStroke(1.dp, slide.accentColor.copy(alpha = 0.3f)),
                            modifier = Modifier.size(34.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = bullet.icon,
                                    contentDescription = null,
                                    tint = slide.accentColor,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Text Column
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = bullet.title,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = bullet.caption,
                                fontSize = 11.5.sp,
                                lineHeight = 15.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))
    }
}

@Composable
private fun SlideHeroVisualStage(slide: OnboardingSlideData) {
    val infiniteTransition = rememberInfiniteTransition(label = "heroStage")
    
    // Slow rotational float for orbital decoration
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(24000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    // Gentle vertical bobbing
    val floatOffset by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    Box(
        modifier = Modifier
            .size(190.dp)
            .offset(y = floatOffset.dp),
        contentAlignment = Alignment.Center
    ) {
        // Rotating Orbital Ring with Celestial Gradient
        Canvas(
            modifier = Modifier
                .size(180.dp)
                .rotate(rotationAngle)
        ) {
            drawCircle(
                brush = Brush.sweepGradient(
                    listOf(
                        slide.accentColor.copy(alpha = 0.6f),
                        slide.secondaryAccent.copy(alpha = 0.2f),
                        Color.Transparent,
                        slide.accentColor.copy(alpha = 0.4f),
                        slide.accentColor.copy(alpha = 0.6f)
                    )
                ),
                style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
            )

            // Small orbital satellite bead
            drawCircle(
                color = slide.accentColor,
                radius = 4.dp.toPx(),
                center = Offset(size.width * 0.95f, size.height * 0.5f)
            )
        }

        // Frosted Glass Core Stage
        Surface(
            shape = RoundedCornerShape(36.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
            border = BorderStroke(
                1.5.dp,
                Brush.linearGradient(
                    listOf(
                        slide.accentColor.copy(alpha = 0.7f),
                        slide.secondaryAccent.copy(alpha = 0.25f),
                        Color.Transparent
                    )
                )
            ),
            shadowElevation = 10.dp,
            modifier = Modifier.size(136.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            listOf(
                                slide.accentColor.copy(alpha = 0.22f),
                                slide.secondaryAccent.copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Central Glowing Icon
                Icon(
                    imageVector = slide.primaryIcon,
                    contentDescription = slide.title,
                    tint = slide.accentColor,
                    modifier = Modifier.size(62.dp)
                )
            }
        }

        // Floating Decorative Glyphs Badges
        slide.decorativeGlyphs.forEachIndexed { index, glyph ->
            val angleDeg = (index * 120.0 + 30.0) * (Math.PI / 180.0)
            val radiusDp = 74.0
            val offsetX = (cos(angleDeg) * radiusDp).toInt()
            val offsetY = (sin(angleDeg) * radiusDp).toInt()

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, slide.accentColor.copy(alpha = 0.45f)),
                shadowElevation = 6.dp,
                modifier = Modifier
                    .offset(x = offsetX.dp, y = offsetY.dp)
                    .size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = glyph,
                        fontSize = if (glyph.length > 2) 10.sp else 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = slide.accentColor,
                        fontFamily = AppTitleFont
                    )
                }
            }
        }
    }
}
