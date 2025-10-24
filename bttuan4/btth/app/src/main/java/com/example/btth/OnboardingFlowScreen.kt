package com.example.btth.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.btth.data.OnboardingItem
import com.example.btth.data.Screen
import com.example.btth.data.onboardingPages
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingFlowScreen(navController: NavController) {

    val pages = onboardingPages
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            OnboardingTopBar(
                onSkip = {
                    // Khi nhấn Skip, chuyển đến màn hình chính và xóa lịch sử navigation
                    navController.navigate(Screen.SPLASH) { // Giả sử bạn có màn hình Home
                        popUpTo(Screen.SPLASH) { inclusive = true }
                    }
                }
            )
        },
        bottomBar = {
            OnboardingBottomBar(
                pagerState = pagerState,
                onNext = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onBack = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                onGetStarted = {
                    // Khi nhấn Get Started, chuyển đến màn hình chính và xóa lịch sử
                    navController.navigate(Screen.ONBOARDING_FLOW) {
                        popUpTo(Screen.ONBOARDING_FLOW) { inclusive = true }
                    }
                }
            )
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { pageIndex ->
            OnboardingPageContent(item = pages[pageIndex])
        }
    }
}

@Composable
fun OnboardingTopBar(onSkip: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextButton(
            onClick = onSkip,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(text = "Bỏ qua", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun OnboardingPageContent(item: OnboardingItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Căn giữa các mục
    ) {
        // Spacer để đẩy nội dung lên trên
        Spacer(modifier = Modifier.weight(2f))

        Image(
            painter = painterResource(id = item.imageResId),
            contentDescription = item.title,
            modifier = Modifier
                .fillMaxWidth(0.8f) // Chiếm 80% chiều rộng
                .aspectRatio(1.2f)   // Giữ tỷ lệ vuông cho ảnh
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = item.title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant // Màu chữ phụ từ theme
            )
        )

        // Spacer quan trọng: Đẩy toàn bộ nội dung lên
        // Tăng weight (ví dụ: 1.2f) để đẩy lên cao hơn
        Spacer(modifier = Modifier.weight(1.2f))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingBottomBar(
    pagerState: PagerState,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onGetStarted: () -> Unit
) {
    val isLastPage = pagerState.currentPage == pagerState.pageCount - 1
    val isFirstPage = pagerState.currentPage == 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Page Indicator (dấu chấm)
        PageIndicator(
            totalPages = pagerState.pageCount,
            currentPage = pagerState.currentPage
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nút Back
            if (!isFirstPage) {
                OutlinedButton( // Dùng OutlinedButton cho nút phụ
                    onClick = onBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Quay lại")
                }
            } else {
                // Giữ chỗ trống để nút Next/GetStarted luôn ở bên phải
                Spacer(modifier = Modifier.weight(1f))
            }

            // Nút Next/Get Started
            Button(
                onClick = if (isLastPage) onGetStarted else onNext,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = if (isLastPage) "Bắt đầu" else "Tiếp theo")
            }
        }
    }
}

@Composable
fun PageIndicator(totalPages: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalPages) { index ->
            val isSelected = index == currentPage
            Box(
                modifier = Modifier
                    .size(if (isSelected) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true, name = "Onboarding Screen Preview")
@Composable
fun OnboardingFlowScreenPreview() {
    MaterialTheme {
        OnboardingFlowScreen(navController = NavController(LocalContext.current))
    }
}
