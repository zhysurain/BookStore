CREATE DATABASE bookstore;

/*用户表*/
CREATE TABLE tb_user(
  uid CHAR(32) PRIMARY KEY,/*主键*/
  username VARCHAR(50) NOT NULL,/*用户名*/
  `password` VARCHAR(50) NOT NULL,/*密码*/
  email VARCHAR(50) NOT NULL,/*邮箱*/
  `code` CHAR(64) NOT NULL,/*激活码*/
  state BOOLEAN/*用户状态，有两种是否激活*/
);

SELECT * FROM tb_user;

/*分类*/
CREATE TABLE category (
  cid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,/*主键*/
  cname VARCHAR(100) NOT NULL/*分类名称*/
)ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8;

INSERT  INTO category(cname) VALUES ('JavaSE');
INSERT  INTO category(cname) VALUES ('JavaEE');
INSERT  INTO category(cname) VALUES ('Javascript');

SELECT * FROM category;

/*图书表*/
CREATE TABLE book (
  bid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,/*主键*/
  bname VARCHAR(100),/*图书名*/
  price DECIMAL(6,2),/*单价*/
  author VARCHAR(20),/*作者*/
  image VARCHAR(200),/*图片*/
  cid int(11),/*所属分类*/
  FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)/*建立主外键关系*/
)ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8;

INSERT  INTO book VALUES ('1','Java编程思想（第4版）','75.6','qdmmy6','book_img/9317290-1_l.jpg','1');
INSERT  INTO book VALUES ('2','Java核心技术卷1','68.5','qdmmy6','book_img/20285763-1_l.jpg','1');
INSERT  INTO book VALUES ('3','Java就业培训教程','39.9','张孝祥','book_img/8758723-1_l.jpg','1');
INSERT  INTO book VALUES ('4','Head First java','47.5','（美）塞若','book_img/9265169-1_l.jpg','1');
INSERT  INTO book VALUES ('5','JavaWeb开发详解','83.3','孙鑫','book_img/22788412-1_l.jpg','2');
INSERT  INTO book VALUES ('6','Struts2深入详解','63.2','孙鑫','book_img/20385925-1_l.jpg','2');
INSERT  INTO book VALUES ('7','精通Hibernate','30.0','孙卫琴','book_img/8991366-1_l.jpg','2');
INSERT  INTO book VALUES ('8','精通Spring2.x','63.2','陈华雄','book_img/20029394-1_l.jpg','2');
INSERT  INTO book VALUES ('9','Javascript权威指南','93.6','（美）弗兰纳根','book_img/22722790-1_l.jpg','3');

SELECT * FROM book;

/*购物车表*/
CREATE TABLE cart (
	ctid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,/*主键*/
	uid CHAR(32),/*用户id*/
	FOREIGN KEY (uid) REFERENCES tb_user(uid)/*建立主外键关系*/
)ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8; 

/*购物车明细表*/
CREATE TABLE cartitem (
	ciid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,/*主键*/
	ctid int(11),/*购物车id*/
	bid int(11),/*book id*/
	`count` int(11),/*书本数量*/
	FOREIGN KEY (ctid) REFERENCES cart(ctid)/*建立主外键关系*/
	FOREIGN KEY (bid) REFERENCES book(bid)/*建立主外键关系*/
)ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8;


/*订单表*/
CREATE TABLE orders (
  oid CHAR(32) NOT NULL PRIMARY KEY,/*主键*/
  ordertime DATETIME,/*订单生成时间*/
  total DECIMAL(10,2),/*订单合计*/
  state SMALLINT(1),/*订单状态：0未付款、1已付款但未发货、2已发货但未确认收货、3收货已结束*/
  uid CHAR(32),/*订单的主人*/
  address VARCHAR(200),/*订单的收货地址*/
  FOREIGN KEY (uid) REFERENCES tb_user(uid)/*建立主外键关系*/
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT * FROM orders;

/*订单项表*/
CREATE TABLE orderitem (
  iid int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,/*主键*/
  price DECIMAL(10,2),/*单价*/
  `count` int(11),/*数量*/
  subtotal DECIMAL(10,2),/*小计*/
  oid CHAR(32),/*所属订单*/
  bid int(11),/*订单项所指的商品*/
  FOREIGN KEY (oid) REFERENCES orders (oid),/*建立主外键关系*/
  FOREIGN KEY (bid) REFERENCES book (bid)/*建立主外键关系*/
)ENGINE=InnoDB auto_increment=1 DEFAULT CHARSET=utf8;

SELECT * FROM orderitem;