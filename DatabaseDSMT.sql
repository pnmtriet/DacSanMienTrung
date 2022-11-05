USE MASTER --CHUYỂN CSDL MẶC ĐỊNH VỀ MASTER
GO --KẾT THÚC KHỐI LỆNH TRƯỚC
IF DB_ID('DSMT') IS NOT NULL --KIỂM TRA CSDL CẦN TẠO ĐÃ TỒN TẠI CHƯA
	DROP DATABASE DSMT --XÓA CSDL
GO
CREATE DATABASE DSMT;
GO
USE DSMT;
GO
--Danh mục sản phẩm
CREATE TABLE Category
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	category_name NVARCHAR(50),
);

-- Thương hiệu
CREATE TABLE Brand
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	brand_name NVARCHAR(70),
);

--Bảng sản phẩm
CREATE TABLE Product
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  product_name NVARCHAR(500),
  price INT,
  discount FLOAT,
  note NVARCHAR(MAX),
  images VARCHAR(MAX),
  unit NVARCHAR(MAX),
  -- Số lần bán
  number_of_sale INT,
  category_id INT,
  brand_id INT,
  FOREIGN KEY (category_id) REFERENCES Category(id),
  FOREIGN KEY (brand_id) REFERENCES Brand(id)
);

--Bảng hình ảnh của sản phẩm
CREATE TABLE ProductImage
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	product_image_name VARCHAR(50),
	product_id INT,
	FOREIGN KEY (product_id) REFERENCES Product(id)
);

-- Nhà Kho
CREATE TABLE Store
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	amount INT,
	product_id INT,
	FOREIGN KEY (product_id) REFERENCES Product(id)
);

-- Đây là Bảng chi tiết quyền
CREATE TABLE Roles
(
	id INT IDENTITY(1,1) PRIMARY KEY,
	role_name NVARCHAR(30)
);
--Đây là Bảng tài khoản
CREATE TABLE Account
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  user_name VARCHAR(20),
  pass_word VARCHAR(20),
  full_name NVARCHAR(50),
  phone VARCHAR(20),
  email VARCHAR(50),
  gender bit,
  address NVARCHAR(300),
  date_of_birth DATE,
  role_id INT,
  FOREIGN KEY (role_id) REFERENCES Roles(id)
);



--Hóa đơn
CREATE TABLE Orders
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  --Ngày đặt hàng
  order_date DATETIME,
  -- Ghi chú của khách hàng
  note NVARCHAR(300),
  --Trạng thái đơn hàng
  order_status NVARCHAR(20),
  --Tổng tiền
  total_money INT,
  --Ngày giao dự kiến (mặc định + lên 3 ngày)
  delivery_date DATE,
  payment NVARCHAR(50),
  fullname NVARCHAR(50),
  phone VARCHAR(20),
  email VARCHAR(50),
  address NVARCHAR(300),
  --Người đặt hàng
  account_id INT,
  FOREIGN KEY (account_id) REFERENCES Account(id)
);

--Chi tiết hóa đơn
CREATE TABLE OrdersDetail
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  order_id INT,
  product_id INT,
  --Số lượng sp
  amount INT,
  --Đơn giá sp
  price INT,
  FOREIGN KEY (order_id) REFERENCES Orders(id),
  FOREIGN KEY (product_id) REFERENCES Product(id)
);

--Tài khoản thêm xóa sửa quản lý riêng
CREATE TABLE AdminAccount
(
  id INT IDENTITY(1,1) PRIMARY KEY,
  admin_user VARCHAR(20),
  admin_password  VARCHAR(20),
  roles  NVARCHAR(20)
);

INSERT INTO Category
VALUES(N'Đồ ăn nhanh'),
(N'Tráng miệng'),
(N'Đồ ăn mặn'),
(N'Bánh kẹo'),
(N'Trà'),
(N'Thực phẩm')

INSERT INTO Brand
VALUES(N'Thanh Hóa'),
(N'Nha Trang'),
(N'Huế'),
(N'Đà Nẵng'),
(N'Nghệ An'),
(N'Ninh Thuận'),
(N'Phan Thiết'),
(N'Quảng Ngãi'),
(N'Quảng Trị'),
(N'Bình Định'),
(N'Quảng Nam'),
(N'Hà Tĩnh')


INSERT INTO Product
VALUES(N'Nem chua Thanh Hóa',100000,0,
N'Nem chua ăn vào có vị giòn của bì lợn hòa quyện cùng vị chua của thịt lên men và vị cay của tỏi, ớt. Ngoài ra, nem chua còn được lồng những chiếc lá đinh lăng và tạo thành hương vị độc đáo khó quên. Bạn có thể chấm nem chua cùng tương ớt và một chút bia lạnh để món ngon tròn vị hơn.',
'sp1.png',N'Phần',10,3,1),
(N'Bún cá Nha Trang',35000,0,
N'Bún cá Nha Trang là món ăn đặc sản miền Trung rất hấp dẫn. Bún cá được làm từ cá bò, cá ngừ hoặc cá bè được lọc sạch xương và tán nhuyễn để nấu nước lèo. Sự hấp dẫn của tô bún cá Nha Trang là vị ngọt thanh hòa quyện chút mặn mòi của cá biển, kết hợp với sợi bún nhỏ, mềm sẽ tạo nên hương vị cực kỳ độc đáo.',
'sp2.png',N'Phần',10,3,2),
(N'Bánh khô mè, mè xửng Huế',70000,0,
N'Nem chua ăn vào có vị giòn của bì lợn hòa quyện cùng vị chua của thịt lên men và vị cay của tỏi, ớt. Ngoài ra, nem chua còn được lồng những chiếc lá đinh lăng và tạo thành hương vị độc đáo khó quên. Bạn có thể chấm nem chua cùng tương ớt và một chút bia lạnh để món ngon tròn vị hơn.',
'sp3.png',N'Kg',10,4,3),
(N'Yến sào Nha Trang',800000,0,
N'Yến sào Nha Trang là đặc sản miền Trung có giá trị dinh dưỡng cao. Đây là món ăn mà bất cứ du khách nào cũng muốn thưởng thức và mang về làm quà cho người thân. Để thưởng thức món ăn đầy chất dinh dưỡng này, bạn có thể chưng yến với đường phèn để ăn được bát yến thơm ngon, bổ dưỡng.',
'sp4.png',N'100 g',10,6,2),
(N'Trà cung đình Huế',140000,0,
N'Trà cung đình Huế là loại trà được vua chúa dùng nên được bào chế tỉ mỉ từ nhiều loại thảo dược quý. Uống một tách trà cung đình Huế ấm nóng sẽ giúp đào thải độc tố, mát gan và ngủ sâu giấc. Vì vậy, đây là món quà rất có ý nghĩa để trao tặng ông bà và cha mẹ khi đi du lịch tại Huế đấy!',
'sp5.png',N'Kg',10,5,3),
(N'Bánh ít lá gai Bình Định',5000,0,
N'Bánh ít lá gai là đặc sản Bình Định nổi tiếng với sự dẻo thơm nhưng không hề dính răng. Bánh là sự hòa quyện hoàn hảo giữa sự ngọt thanh của đường và chút dẻo thơm từ gạo nếp. Bánh ít lá gai là đặc sản tại Bình Định, nhưng sau này cách làm bánh được phổ biến khắp các vùng ven biển miền Trung và trở thành đặc sản chung của Việt Nam.',
'sp6.png',N'Phần',10,4,10),
(N'Cao lầu Quảng Nam',50000,0,
N'Một trong những món ăn Quảng Nam luôn lôi cuốn khách du lịch là cao lầu. Sợi mì cao lầu nổi tiếng với độ dai giòn được làm ra từ nhiều công đoạn phức tạp. Sợi mì cao lầu được ăn cùng thịt heo thái mỏng và chan nước dùng. Sau đó ăn kèm với rau đắng tạo nên hương vị độc đáo khó quên.',
'sp7.png',N'Phần',10,3,11),
(N'Mì Quảng',25000,0,
N'Mì Quảng là món ăn nổi tiếng với hương vị lôi cuốn hấp dẫn. Nhưng nhưng để thưởng thức món mì Quảng chuẩn vị, bạn cần đến Quảng Nam - Đà Nẵng. Mì Quảng được ăn kèm với tôm, thịt heo, thịt gà và nước lèo đậm vị. Món ăn này phải ăn kèm rau sống và bánh đa mới tạo nên hương vị hoàn hảo nhất.',
'sp8.png',N'Phần',10,3,11),
(N'Mực nhảy Vũng Áng',700000,10,
N'Khi du lịch đến Hà Tĩnh , bạn nhất định phải ăn mực nhảy Vũng Áng, một loài mực với vẻ ngoài lấp lánh như vì sao. Sở dĩ món ăn này có tên gọi như vậy là do mực khi được đánh bắt vào bờ sẽ nhảy lên tanh tách trông vui mắt. Loại mực này khi chế biến có vị thơm, giòn và ngọt, khi ăn được chấm cùng tương ớt thì sẽ ngon quên lối về.',
'sp9.png',N'Kg',10,6,12),
(N'Bánh ướt Huế',25000,0,
N'Bánh ướt Huế là đặc sản miền Trung cực kỳ nổi tiếng. Món ăn này thường được ăn nóng với chút mỡ hành và hành phi. Sau đó ăn kèm với thịt heo nướng hoặc thịt quay giòn rụm. Bánh ướt Huế được chấm cùng nước mắm pha tỏi, ớt, đường và chanh tạo nên một món ngon khó quên khi du lịch miền Trung.',
'sp10.png',N'Phần',10,3,3),
(N'Bún bò Huế',30000,0,
N'Bún bò Huế là một món ngon quá đỗi quen thuộc với nhiều người. Nhưng món bún bò xứ Huế lại khiến bất kì du khách nào cũng phải say mê. Nước dùng bún bò được làm từ xương hầm đậm đà ăn kèm chút mắm ruốc mặn mà, thêm một khoanh giò mềm dai cùng miếng chả Huế giòn. Đây chắc chắn là món ngon không thể bỏ qua khi du lịch xứ Huế đấy!',
'sp11.png',N'Phần',10,3,3),
(N'Cháo lươn Nghệ An',20000,0,
N'Lươn là một trong nhưng đặc sản Nghệ An nổi tiếng. Món cháo lươn xứ Nghệ là món ngon mà bạn không thể bỏ lỡ khi đến đây. Món cháo lươn ngon phải được nấu với lươn đồng để có thịt chắc, ngọt. Khi ăn, cháo lươn được ăn cùng hành lá và rau răm để tạo vị thơm hấp dẫn.',
'sp12.png',N'Phần',10,3,5),
(N'Bánh cuốn Thanh Hóa',30000,0,
N'Bánh cuốn Thanh Hóa cũng là đặc sản miền Trung nổi tiếng không kém nem chua. Món bánh cuốn này được tráng một lớp mỏng dính nhưng lại dai ngon bất ngờ. Bên trong lớp bánh này là nhân thịt heo, tôm, nấm hương được nêm nếm đậm đà. Ăn kèm món bánh cuốn cùng hành phi và nước mắm tỏi ớt sẽ khiến bạn cảm nhận được vị ngon khó quên.',
'sp13.png',N'Phần',10,3,1),
(N'Bánh xèo tôm nhảy Bình Định',30000,0,
N'Là một trong những món đặc sản nức tiếng của miền đất võ Bình Định. Ngay từ tên gọi, đã gây được sự tò mò, hứng thú của du thực khách. Món này có cái tên hay như vậy bởi những nguyên liệu đặc biệt để tạo nên món bánh hấp dẫn chính là những con tôm đất đỏ au tròn mẩy, vừa được đánh bắt còn nhảy đành đạch',
'sp14.png',N'Phần',10,3,10),
(N'Bánh canh cá lóc Quảng Trị',25000,0,
N'Đây là một trong các món ăn miền Trung phổ biến ở khu vực Bình Trị Thiên. Món bánh canh cá lóc có thành phần khá đơn giản, nguyên liệu chính theo đúng tên gọi đó là bánh canh kết hợp với cá lóc đồng (cá tràu).',
'sp15.png',N'Phần',10,3,9)


insert into Roles
VALUES ('Admin'),
('Staff'),
('User')

insert into Account
values ('pnmtriet','123456',N'Phạm Nguyễn Minh Triết','0393796446','pnmtriet@gmail.com',0,N'123 Cộng Hòa, Q.Tân Bình, TP.HCM','1999/08/22',1),
('pnmtriet2','123456',N'Phạm Nguyễn Minh Triết','0393796446','pnmtriet@gmail.com',0,N'123 Cộng Hòa, Q.Tân Bình, TP.HCM','1999/08/22',2),
('pnmtriet3','123456',N'Phạm Nguyễn Minh Triết','0393796446','pnmtriet193@gmail.com',0,N'123 Cộng Hòa, Q.Tân Bình, TP.HCM','1999/08/22',3)



--select * from Product
--select * from Category
--select * from Brand
--select * from Roles
--select * from Account
--select * from Orders
--select * from OrdersDetail

