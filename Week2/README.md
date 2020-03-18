# Báo cáo tuần 2
## Những công việc đã làm
  - Tìm hiểu bài toán phát hiện sai khác trong ảnh - áp dụng phát hiện ảnh quảng cáo bị thay đổi - (che khuất, mờ, ....)
  - Phân tích cấu trúc, chức năng, use case của hệ thống sẽ làm

## Kết quả
### Tìm hiểu bài toán phát hiện sai khác trong ảnh
#### Các thuật toán, phương pháp có thể nhận ra sai khác
 - Training với các hình ảnh của đối tượng bình thường + các hình ảnh đối tượng khác thường + kiểm tra nhiều vùng trên toàn bức ảnh đánh giá detection score và sự phụ thuộc lẫn nhau giữa các vùng để phát hiện sự xuất hiện của đối tượng khác thường trong đối tượng bình thường (What’s Wrong with that Object? Identifying Images of Unusual Objects by Modelling the Detection Score Distribution∗)
 - Sử dụng học không có giám sát để phát hiện các điểm bất thường trên bề mặt kim loại (nhận diện trên hình ảnh có cấu trúc) (Anomaly Detection using Deep Learning based Image Completion)
 - Sử dụng mạng mới AnoGAN để phát hiện sai khác (Unsupervised Anomaly Detection with Generative Adversarial Networks to Guide Marker Discovery) - bài này em không hiểu nhưng có code chạy thử trong nhận diện sai khác chữ viết em thấy cũng chạy được
 - Một số phương pháp khác như Fisher Vector, FV + SVM, Siamese Architecture nhưng tỏ ra không hiệu quả khi xuất hiện những điểm bất thường nhỏ trên ảnh
 - 
### Tìm hiểu Tensorflow và thử áp dụng object detection nhận diện các lá bài
- Một thư viện mã nguồn mở dễ sử dụng, nhiều tutorial có sẵn, cộng đồng hỗ trợ đông đảo, có thể triển khai trên nhiều nền tảng
- Chạy thử tutorial bước đầu đã có kết quả
- Các bước đã thực hiện
  - Cài đặt các thư viện, driver cho GPU NVidia, cài đặt Anaconda và Virtual Environment, cài đặt Tensorflow-GPU lên môi trường ảo
  - Sử dụng project mẫu của Tensorflow về object detection
  - Thu thập hình ảnh và dán nhãn bẳng tay sử dụng [LabelImg](https://github.com/tzutalin/labelImg#installation)
  - Khởi tạo, cài đặt tham số training data
  - Training (trong 3h bằng GPU)
  - Export inference graph
  - Test, kết quả [tại đây](https://github.com/datnd299/Graduation-Project/tree/master/Week1/Ket%20qua)

## Công việc tuần tới
- Tiếp tục tìm hiểu Tensorflow, cải thiện các chức năng, độ chính xác của nhận diện cho phù hợp với bài toán
- Tiếp tục tìm hiểu các thuật ngữ, bài toán cơ bản trong deep learning
- Phân tích chức năng, use cases của hệ thống sẽ làm
