# Mô tả cơ bản + 
Ý tưởng cốt lõi là Compose và hệ thống View có thể tồn tại song song trong cùng một ứng dụng, thậm chí trong cùng một màn hình. Điều này cho phép:
- 1.Áp dụng dần dần (Gradual Adoption): Bắt đầu viết các màn hình mới bằng Compose, trong khi vẫn giữ lại các màn hình cũ viết bằng XML.
- 2.Tái sử dụng linh hoạt:
  - Đặt một thành phần giao diện (UI Component) được viết bằng Compose vào bên trong một layout XML.
  - Đặt một View truyền thống (như MapView, AdView) vào bên trong một màn hình Compose.

# Cách cài đặt
- Mở dự án bằng Android Studio.
- Android Studio sẽ tự động đồng bộ Gradle. Quá trình này có thể mất vài phút.
- Nếu gặp lỗi org.gradle.java.home, hãy vào File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle và chọn một Gradle JDK là jbr-17 hoặc Embedded JDK 17.
# Chạy Ứng Dụng:
- Kết nối một thiết bị Android thật hoặc khởi động một máy ảo (Emulator).
- Nhấn nút Run 'app' (▶️) trên thanh công cụ của Android Studio.
