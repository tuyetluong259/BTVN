package com.example.btvn1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.btvn1.ui.theme.Btvn1Theme // <-- Nhớ import Theme của bạn

// --- 1. Định nghĩa các Màn hình (Routes) ---

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object QuanLy : Screen("quanly", Icons.Default.Home, "Quản lý")
    object DSSach : Screen("dssach", Icons.Default.List, "DS Sách")
    object SinhVien : Screen("sinhvien", Icons.Default.Person, "Sinh viên")
}

// --- 2. Ứng dụng chính và Thanh Điều hướng ---

@Composable
fun LibraryApp() {
    // Bọc ứng dụng trong Theme để có giao diện đồng nhất
    Btvn1Theme {
        val navController = rememberNavController()
        val items = listOf(Screen.QuanLy, Screen.DSSach, Screen.SinhVien)

        Scaffold(
            bottomBar = {
                // Đã chuyển sang NavigationBar của Material3
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.label) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            // Container cho nội dung các màn hình
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Tách NavHost ra một Composable riêng
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController,
        startDestination = Screen.QuanLy.route,
        modifier = modifier
    ) {
        composable(Screen.QuanLy.route) { QuanLyScreen() }
        composable(Screen.DSSach.route) { DSSachScreen() }
        composable(Screen.SinhVien.route) { SinhVienScreen() }
    }
}


// --- 3. Màn hình Chi tiết: Quản lý (Theo ảnh mẫu) ---

@Composable
fun QuanLyScreen() {
    var currentStudent by remember { mutableStateOf("Nguyen Van A") }
    val borrowedBooks = remember {
        mutableStateListOf(
            "Sách 01" to true,
            "Sách 02" to true
        )
    }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Hệ thống Quản lý Thư viện", style = MaterialTheme.typography.headlineSmall)

        // Khu vực Sinh viên và nút Thay đổi
        Text("Sinh viên", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = currentStudent,
                onValueChange = { /* Chỉ đọc */ },
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                currentStudent = if (currentStudent == "Nguyen Van A") "Nguyen Thi B" else "Nguyen Van A"
            }) {
                Text("Thay đổi")
            }
        }

        Text("Danh sách sách", style = MaterialTheme.typography.titleMedium)

        // Danh sách sách đang mượn
        if (borrowedBooks.isEmpty() && currentStudent == "Nguyen Van A") {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Bạn chưa mượn quyển sách nào.\nNhấn 'Thêm' để bắt đầu hành trình đọc sách!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                borrowedBooks.forEachIndexed { index, (bookName, isChecked) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { newCheck ->
                                    borrowedBooks[index] = bookName to newCheck
                                }
                            )
                            Text(bookName, modifier = Modifier.weight(1f).padding(start = 8.dp))
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f)) // Đẩy nút Thêm xuống dưới

        // Nút Thêm sách
        Button(
            onClick = {
                if (currentStudent == "Nguyen Van A" && !borrowedBooks.any { it.first == "Sách 03" }) {
                    borrowedBooks.add("Sách 03" to true)
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Thêm", fontSize = 18.sp)
        }
    }
}

// --- 4. Màn hình Chi tiết: DS Sách ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DSSachScreen() {
    var searchQuery by remember { mutableStateOf("") }
    val allBooks = remember {
        listOf(
            Triple("Sách Lập trình Java", "James Gosling", 10 to 5),
            Triple("Sách Toán Cao cấp", "Nguyễn Văn Hải", 8 to 8),
            Triple("Sách Marketing 101", "Philip Kotler", 5 to 0),
            Triple("Lịch sử Việt Nam", "Trần Trọng Kim", 12 to 11)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách Sách (Kho)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Tìm kiếm sách...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true
            )
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(allBooks) { (name, author, count) ->
                    BookItem(name, author, count.first, count.second)
                    HorizontalDivider() // Thay thế Divider cũ
                }
            }
        }
    }
}

@Composable
fun BookItem(name: String, author: String, total: Int, available: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Text("Tác giả: $author", style = MaterialTheme.typography.bodySmall)
        }
        Column(horizontalAlignment = Alignment.End) {
            val color = if (available > 0) Color(0xFF008000) else Color.Red
            Text(
                "Còn $available/$total",
                color = color,
                fontWeight = FontWeight.Bold
            )
            Text("Đang mượn: ${total - available}", style = MaterialTheme.typography.bodySmall)
        }
    }
}

// --- 5. Màn hình Chi tiết: Sinh viên ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SinhVienScreen() {
    var search by remember { mutableStateOf("") }
    val students = remember {
        listOf(
            Triple("21HK6001", "Nguyễn Văn A", 2),
            Triple("21HK6002", "Nguyễn Thị B", 1),
            Triple("21HK6003", "Nguyễn Văn C", 0),
            Triple("21HK6004", "Trần Thị D", 5)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Danh sách Sinh viên") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TextField(
                value = search,
                onValueChange = { search = it },
                label = { Text("Tìm kiếm sinh viên...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true
            )
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(students) { (id, name, borrowedCount) ->
                    StudentItem(id, name, borrowedCount)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun StudentItem(id: String, name: String, borrowedCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Xem chi tiết sinh viên */ }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Text("Mã SV: $id", style = MaterialTheme.typography.bodySmall)
        }
        Text(
            "$borrowedCount cuốn",
            style = MaterialTheme.typography.bodyLarge,
            color = if (borrowedCount > 0) MaterialTheme.colorScheme.primary else Color.Gray,
            fontWeight = FontWeight.Bold
        )
    }
}
