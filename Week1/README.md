# Báo cáo tuần 1
## Những công việc đã làm
  - Tìm hiểu bài báo về dataset 1M ảnh người nổi tiếng
  - Tìm hiểu Tensorflow và thử áp dụng object detection nhận diện các lá bài

## Kết quả
### Bài báo về dataset 1M ảnh người nổi tiếng
 - Đã có hiểu biết sơ bộ về các bước họ làm
 - Chưa thực sự hiểu sâu do có các thuật ngữ trong deep learning cần tìm hiểu thêm
 - Báo cáo chi tiết tại file pttx
### Tìm hiểu Tensorflow và thử áp dụng object detection nhận diện các lá bài
- Một thư viện mã nguồn mở dễ sử dụng, nhiều tutorial có sẵn, cộng đồng hỗ trợ đông đảo, có thể triển khai trên nhiều nền tảng
- Chạy thử tutorial bước đầu đã có kết quả
- Các bước đã thực hiện
-- Cài đặt các thư viện, driver cho GPU NVidia, cài đặt Anaconda và Virtual Environment, cài đặt Tensorflow-GPU lên môi trường ảo
-- Sử dụng project mẫu của Tensorflow về object detection
-- Thu thập hình ảnh và dán nhãn bẳng tay sử dụng [LabelImg](https://github.com/tzutalin/labelImg#installation)
-- Khởi tạo, cài đặt tham số training data
-- Training (trong 3h bằng GPU)
-- Export inference graph
-- Test, kết quả [tại đây](https://github.com/datnd299/Graduation-Project/tree/master/Week1/Ket%20qua)

## Công việc tuần tới
- Tiếp tục tìm hiểu Tensorflow, cải thiện các chức năng, độ chính xác của nhận diện cho phù hợp với bài toán
- Tiếp tục tìm hiểu các thuật ngữ, bài toán cơ bản trong deep learning
- Phân tích chức năng, use cases của hệ thống sẽ làm
