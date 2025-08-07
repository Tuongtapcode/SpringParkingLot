import { Spinner } from "react-bootstrap";

const MySpinner = () => {
  return (
    <div className="d-flex flex-column align-items-center">
      <Spinner animation="border" variant="primary" />
      <span className="mt-2">Loading...</span>
    </div>
  );
};

export default MySpinner;
