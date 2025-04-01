## PRJ321x Project2

PRJ321x Project2
Để hình dung tổng quát nhất, bạn có thể theo dõi một trang web mẫu tại [đây](https://www.topcv.vn/viec-lam).

## Overview
Nhu cầu tìm kiếm nhân sự của các doanh nghiệp cũng như tìm kiếm việc làm của các ứng viên ngày càng nhiều và trở nên cấp thiết. Vì vậy, việc thiết kế website tuyển dụng cùng với sự phát triển mạnh mẽ của công nghệ thông tin như hiện nay để đáp ứng nhu cầu của các doanh nghiệp tìm kiếm nhân sự và ứng viên tìm kiếm nhà tuyển dụng thông qua mạng internet cụ thể là ngày càng trở nên cấp thiết

Hiện nay các nhà đầu tư về lĩnh vực tuyển dụng, headhunter đa số đều đã nắm bắt cơ hội, tạo ra một cầu nối trung gian giữa nhà tuyển dụng và ứng viên bằng cách phát triển nó thành một website tuyển dụng thực tế thì điều đó thật là tuyệt vời và đem lại các lợi ích vô cùng lớn.

Bài tập này yêu cầu học viên xây dựng một website tuyển dụng để tạo ra một cầu nối trung gian giữa nhà tuyển dụng và các ứng viên để đáp ứng nhu cầu tìm kiếm nhân sự của các doanh nghiệp cũng như tìm kiếm việc làm của các ứng viên thông qua mạng internet.
Về mặt xây dựng và tổ chức dự án, học viên sẽ phải tự thiết kế theo mô hình khuôn mẫu của MVC, tức là sẽ có các lớp package như: model, view, controller, config,...
## Prerequisites
* Docker
* Docker Compose
## Build and Run (Windows)

### 1. Build WAR

```bash
mvn clean package
```

### 2. Run

```bash
docker-compose up --build
```
### 3. Using Sending Mail Feature (Optional)
> 1. Enable "2-step verification" in https://myaccount.google.com/lesssecureapps
> 2. Generate "App password" at https://myaccount.google.com/apppasswords
> 3. Set "EMAIL_USERNAME" and "EMAIL_PASSWORD" (the generated password from step 2) in file ".env"
> 4. Run "docker-compose up --build" again