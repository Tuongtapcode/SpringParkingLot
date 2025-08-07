import { Alert, Container } from "react-bootstrap";
import { Link } from "react-router-dom";

const AlertNoUser = ({ title, urlNext}) => {
  return (
    <>
      <Container className="py-4">
        <Alert variant="warning" className="text-center">
          <Alert.Heading>Bạn chưa đăng nhập</Alert.Heading>
          <p>
            Vui lòng <Link to={urlNext}>Đăng nhập</Link> để xem và quản lý{" "}
            {title} của bạn.
          </p>
        </Alert>
      </Container>
    </>
  );
};

export default AlertNoUser;
