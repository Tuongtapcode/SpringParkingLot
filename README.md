# 🚗 Hệ Thống Bãi Xe Thông Minh

Một hệ thống quản lý bãi đỗ xe hiện đại, cho phép người dùng tìm kiếm, đặt chỗ và thanh toán cho chỗ đỗ xe trực tuyến một cách tiện lợi và hiệu quả.

## 🛠️ Công nghệ sử dụng

### Backend
- **Framework**: Spring MVC
- **Bảo mật**: Spring Security
- **Cơ sở dữ liệu**: MySQL

### Frontend
- **Framework**: React.js

## ✨ Tính năng chính

### 1. 🏢 Quản lý Bãi Đỗ Xe
- **Quản trị bãi đỗ xe**: Quản trị viên có thể thêm mới, chỉnh sửa hoặc xóa các bãi đỗ xe
- **Quản lý thông tin chi tiết**: Cập nhật địa chỉ, số lượng chỗ đỗ, giá cả và các tiện ích đi kèm
- **Theo dõi trạng thái**: Quản lý trạng thái các chỗ đỗ xe (trống, đã đặt, đang sử dụng, bảo trì)

### 2. 🔍 Tìm kiếm và Đặt chỗ
- **Tìm kiếm thông minh**: Tìm kiếm chỗ đỗ xe theo vị trí, thời gian và các tiêu chí khác
- **Bộ lọc nâng cao**: Lọc và sắp xếp theo giá, khoảng cách, đánh giá
- **Đặt chỗ trực tuyến**: Đặt chỗ và thanh toán trực tiếp trên hệ thống

### 3. 👤 Quản lý Người Dùng
- **Xác thực bảo mật**: Đăng ký và đăng nhập với xác thực hai yếu tố
- **Quản lý thông tin**: Cập nhật thông tin cá nhân, thông tin xe và lịch sử đặt chỗ

### 4. 💳 Thanh toán Trực tuyến *(Đang phát triển)*
- **Đa phương thức thanh toán**: Hỗ trợ thẻ tín dụng, thẻ ghi nợ, ví điện tử
- **Hóa đơn điện tử**: Cung cấp hóa đơn và biên lai sau khi thanh toán
- **Chính sách linh hoạt**: Hỗ trợ hoàn tiền và hủy đặt chỗ

### 5. ⏰ Theo dõi và Quản lý *(Đang phát triển)*
- **Theo dõi thời gian**: Giám sát thời gian đỗ xe của người dùng
- **Thông báo thông minh**: Nhắc nhở về thời gian kết thúc và các sự kiện quan trọng *(Đang phát triển)*

### 6. 🌟 Tính năng Xã hội *(Đang phát triển)*
- **Đánh giá và nhận xét**: Người dùng có thể đánh giá bãi đỗ xe sau khi sử dụng
- **Hỗ trợ khách hàng**: Chat trực tiếp, email và điện thoại

## 🚀 Cài đặt và Chạy dự án

### Yêu cầu hệ thống
- Java tôi dùng: Java 21
- Node.js và npm
- MySQL
- Maven

### Backend (Spring Boot)

#### 1. **Clone source code**
```bash
git clone https://github.com/Tuongtapcode/SpringParkingLot.git
cd SpringParkingLot
```

#### 2. **Cài đặt yêu cầu**
- **Java JDK 17+**
- **Maven** (hoặc dùng Maven embedded của IntelliJ)
- **MySQL** (hoặc database bạn muốn)

#### 3. **Cấu hình database**
- Mở file `src/main/resources/application.properties`
- Sửa các thông tin kết nối MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/parkinglot_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

**Lưu ý**: Tạo trước database `parkinglot_db` trên MySQL.

#### 4. **Build và chạy ứng dụng**
- **Nếu dùng Maven từ terminal**:
```bash
mvn clean package
```

- **Nếu dùng IntelliJ**: 
  - Open project → Maven Tool Window → Lifecycle → **package**
- File war sẽ nằm trong target/ParkingLot-1.0-SNAPSHOT.war
- **Deploy WAR vào Tomcat**:
  - Copy WAR vào webapps Tomcat hoặc thêm artifact trong IntelliJ → Run Tomcat
#### 5. **Truy cập API / backend**
- Mặc định chạy trên port `8080`:
```
http://localhost:8080/
```

### Frontend (React.js)
```bash
# Di chuyển vào thư mục frontend
cd ParkingLot\react-parkinglot

# Cài đặt dependencies
npm install

# Chạy ứng dụng React
npm start
```

### Cơ sở dữ liệu
```sql
-- Tạo database
CREATE DATABASE parking_lot_db;

-- Import schema từ file sql (Tôi sẽ push lên sau)
-- mysql -u username -p parking_lot_db < schema.sql
```

## 📁 Cấu trúc dự án

```
SpringParkingLot/
├── src/main/java/com.nnt/      # Backend Spring Boot
│   ├── controllers/           # Controllers & REST Controllers
│   ├── services/              # Business Logic
│   ├── repository/            # Data Access Layer
│   ├── pojo/                  # Entity Classes
│   ├── filters/               # Filter Classes
│   ├── configs/               # Configuration Classes
│   ├── formatters/            # Data Formatters
│   └── utils/                 # Utility Classes
├── src/main/resources/         # Resources
│   ├── databases.properties   # Database Config
│   ├── templates/             # Thymeleaf Templates
│   └── static/                # Static Resources
├── react-parkinglot/          # React Application
│   ├── src/
│        ├── components/        # React Components
│        ├── configs/           # Configuration Files
│        ├── reducers/          # Redux Reducers
│        └── ...                # Other React directories
│  
└── README.md      
```

## 🔧 API Endpoints

### Xác thực
- `POST /api/login` - Đăng nhập
- `POST /api/register` - Đăng ký
### User 
- `GET /secure/profile` - Lấy thông tin cá nhân
- `PUT /secure/profile` - Cập nhật thông tin cá nhân
### Bãi đỗ xe 
- `GET /api/parking-lots` - Lấy danh sách bãi đỗ xe (user)
- `GET /parking-lots` - Lấy danh sách bãi đỗ xe (admin)
- `POST /parking-lots` - Tạo bãi đỗ xe mới
- `GET /parking-lots/{id}` - Chi tiết thông tin bãi đỗ xe
- `POST /parking-lots/{id}` - Cập nhật/thêm mới thông tin bãi đỗ xe
- `DELETE /api/parking-lots/{id}` - Xóa bãi đỗ xe
### Chỗ đỗ xe
- `GET /parkinglots/{parkingLotId}/spaces` - Lấy danh sách chỗ đỗ xe theo bãi đỗ xe(user)
- `GET /parkingspace` - Lấy danh sách chỗ đỗ xe (user)
- `GET /parkingspaces` - Lấy danh sách chỗ đỗ xe (admin)
- `POST /parkingspaces` - Tạo bãi chỗ xe mới
- `GET /parkingspaces/{id}` - Chi tiết thông tin chỗ đỗ xe
- `POST /parkingspaces/{id}` - Cập nhật/thêm mới thông tin chỗ đỗ xe
- `DELETE /api/parkingspaces/{id}` - Xóa chỗ đỗ xe
### Thanh toán
- `GET /api/secure/pays` - Lấy danh sách thanh toán của user
- `POST /api/secure/pays` - Thanh toán 
### Đặt chỗ (Reservation)
- `POST /api/secure/reservation` - Đặt chỗ đỗ xe
- `GET /api/secure/upcoming/pending` - Lấy lịch sử đặt chỗ 
- `POST /api/secure/reservation/{id}/cancel` - Hủy đặt chỗ
## 🤝 Đóng góp
## 📞 Liên hệ

- **Tác giả**: [Tuongtapcode](https://github.com/Tuongtapcode)
- **Email**: [Nguyen Tuong]
- **GitHub**: https://github.com/Tuongtapcode/SpringParkingLot

⭐ **Nếu dự án này hữu ích, hãy cho chúng tôi một star!** ⭐
