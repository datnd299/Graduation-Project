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
 - Xếp chồng 2 hình ảnh (ảnh gốc + ảnh chụp tại thời điểm) sau đó dùng object detetion để tìm điểm khác biệt áp dụng vào bài toán tìm các trang sách bị sửa đổi (Spot the Difference by Object Detection) em thấy bài này ý tưởng dễ hiểu, phù hợp với bài toán của em, không có code mẫu, chi tiết tại [File pptx](https://github.com/datnd299/Graduation-Project/blob/master/Week2/w2.pptx)
### Phân tích cấu trúc, chức năng, use case của hệ thống
- Chi tiết tại [File docx](https://github.com/datnd299/Graduation-Project/blob/master/Week2/w2.docx)

## Công việc tuần tới
- Tiếp tục tìm hiểu bài toán phát hiện sai khác
- Phân tích mở rộng hệ thống
- Dựng các thành phần cơ bản trước của hệ thống
