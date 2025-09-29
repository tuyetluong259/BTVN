1. Mô tả bài tập, mục tiêu và kết quả đạt được
Bài tập yêu cầu tạo một giao diện hồ sơ cá nhân (Profile Card) bằng Jetpack Compose trong Android Studio.
Mục tiêu là rèn luyện cách sử dụng Composable functions, bố cục Column, Box, hiển thị hình ảnh, văn bản, và áp dụng Material 3 để tạo một giao diện đẹp, hiện đại.
Kết quả: chương trình chạy thành công, hiển thị một thẻ (Card) chứa ảnh đại diện, tên, địa chỉ và có hiệu ứng nền gradient.

2. Giải thích các hàm/chức năng chính

* `setContent { ... }`
  Dùng để khai báo giao diện Compose trong Activity.

* `Surface(...) { ProfileScreen() }`
  Tạo bề mặt nền theo Material Design, sau đó gọi hàm `ProfileScreen()` để hiển thị nội dung chính.

* `@Composable fun ProfileScreen()`
  Hàm Composable chứa toàn bộ bố cục của giao diện hồ sơ.

* `Box(...)`
  Dùng để xếp chồng các thành phần, ở đây là tạo nền gradient và căn giữa Card.

* `Card(...)`
  Tạo một thẻ với góc bo tròn, đổ bóng, chứa nội dung hồ sơ.

* `Image(...)`
  Hiển thị ảnh đại diện, có bo tròn bằng `clip(CircleShape)`.

* `Text(...)`
  Hiển thị văn bản: tên ("Ánh Tuyết") và địa chỉ ("Quận 12, Việt Nam").
* 
* `Spacer(...)`
  Tạo khoảng cách giữa các thành phần để giao diện cân đối hơn.
* 
* `Button(...)` *(đang comment trong code)*
  Dùng để tạo nút “Follow” (có thể mở lại nếu muốn thêm chức năng).
3. Kết quả (Output)
Ảnh dưới đây là kết quả chạy ứng dụng, hiển thị thẻ hồ sơ với ảnh đại diện, tên và địa chỉ.
   ![img.png](img.png)

→ Giao diện hiển thị đúng như mong đợi, thể hiện khả năng áp dụng Jetpack Compose để thiết kế UI hiện đại.
