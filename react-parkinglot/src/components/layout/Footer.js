import {
  Car,
  Clock,
  Facebook,
  Instagram,
  Linkedin,
  Mail,
  MapPin,
  Phone,
  Twitter,
} from "lucide-react";
import { Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const Footer = () => {
  return (
    <>
      <div className="bg-dark text-light py-5">
        <Container>
          <div className="row g-4">
            {/* Company Info */}
            <div className="col-lg-4 col-md-6">
              <div className="d-flex align-items-center mb-3">
                <Car className="me-2" size={32} />
                <h5 className="mb-0 fw-bold">ParkEasy</h5>
              </div>
              <p className="text-light-emphasis mb-3">
                Hệ thống bãi xe thông minh hàng đầu Việt Nam. Chúng tôi cung cấp
                dịch vụ đỗ xe an toàn, tiện lợi với công nghệ hiện đại.
              </p>
              <div className="d-flex gap-3">
                <span className="text-light-emphasis hover-text-primary">
                  <Facebook size={24} />
                </span>
                <span className="text-light-emphasis hover-text-primary">
                  <Instagram size={24} />
                </span>
                <span className="text-light-emphasis hover-text-primary">
                  <Twitter size={24} />
                </span>
                <span className="text-light-emphasis hover-text-primary">
                  <Linkedin size={24} />
                </span>
              </div>
            </div>

            {/* Quick Links */}
            <div className="col-lg-2 col-md-6">
              <h6 className="fw-bold mb-3">Liên kết nhanh</h6>
              <ul className="list-unstyled">
                <li className="mb-2">
                  <Link
                    to="/"
                    className="text-light-emphasis text-decoration-none hover-  text-primary"
                  >
                    Trang chủ
                  </Link>
                </li>
                <li className="mb-2">
                  <Link
                    to="/space"
                    className="text-light-emphasis text-decoration-none hover-text-primary"
                  >
                    Dịch vụ
                  </Link>
                </li>
                <li className="mb-2">
                  <Link
                    to="/pricing"
                    className="text-light-emphasis text-decoration-none hover-text-primary"
                  >
                    Bảng giá
                  </Link>
                </li>
                <li className="mb-2">
                  <Link
                    to="/about"
                    className="text-light-emphasis text-decoration-none hover-text-primary"
                  >
                    Về chúng tôi
                  </Link>
                </li>
              </ul>
            </div>

            {/* Services */}
            <div className="col-lg-3 col-md-6">
              <h6 className="fw-bold mb-3">Dịch vụ</h6>
              <ul className="list-unstyled">
                <li className="mb-2">
                  <span className="text-light-emphasis hover-text-primary">
                    Đỗ xe theo giờ
                  </span>
                </li>
                <li className="mb-2">
                  <span className="text-light-emphasis hover-text-primary">
                    Đỗ xe theo tháng
                  </span>
                </li>
                <li className="mb-2">
                  <span className="text-light-emphasis hover-text-primary">
                    Đặt chỗ trước
                  </span>
                </li>
                <li className="mb-2">
                  <span className="text-light-emphasis hover-text-primary">
                    Rửa xe
                  </span>
                </li>
              </ul>
            </div>

            {/* Contact Info */}
            <div className="col-lg-3 col-md-6">
              <h6 className="fw-bold mb-3">Liên hệ</h6>
              <div className="d-flex align-items-center mb-2">
                <MapPin size={16} className="me-2 text-primary" />
                <small className="text-primary">
                  123 Nguyễn Huệ, Q.1, TP.HCM
                </small>
              </div>
              <div className="d-flex align-items-center mb-2">
                <Phone size={16} className="me-2 text-primary" />
                <small className="text-primary">(028) 1234 5678</small>
              </div>
              <div className="d-flex align-items-center mb-2">
                <Mail size={16} className="me-2 text-primary" />
                <small className="text-primary">info@parkeasy.vn</small>
              </div>
              <div className="d-flex align-items-center">
                <Clock size={16} className="me-2 text-primary" />
                <small className="text-primary">24/7 - Mở cửa cả tuần</small>
              </div>
            </div>
          </div>

          <hr className="my-4 border-secondary" />

          {/* Copyright */}
          <div className="row align-items-center">
            <div className="col-md-6">
              <small className="text-light-emphasis">
                © 2025 ParkEasy. All rights reserved.
              </small>
            </div>
            <div className="col-md-6 text-md-end">
              <small>
                <span className="text-light-emphasis me-3 hover-text-primary">
                  Chính sách bảo mật
                </span>
                <span className="text-light-emphasis hover-text-primary">
                  Điều khoản sử dụng
                </span>
              </small>
            </div>
          </div>
        </Container>
      </div>
    </>
  );
};

export default Footer;
