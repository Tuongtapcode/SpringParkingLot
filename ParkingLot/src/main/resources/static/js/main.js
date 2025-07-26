function deleteParkingLot(endpoint) {
  if (confirm("Bạn có chắc chắn muốn xóa bãi đỗ xe này?")) {
    // API call to delete parking lot
    fetch(endpoint, {
      method: "DELETE",
    }).then((response) => {
      if (response.status === 204) {
        alert("Đã xóa bãi đỗ xe thành công!");
        location.reload();
      } else {
        alert("Xóa bãi đỗ xe thất bại!");
      }
    });
  }
}

function deleteParkingSpace(endpoint) {
  if (confirm("Bạn có chắc chắn muốn xóa bãi đỗ xe này?")) {
    // API call to delete parking lot
    fetch(endpoint, {
      method: "DELETE",
    }).then((response) => {
      if (response.status === 204) {
        alert("Đã xóa bãi đỗ xe thành công!");
        location.reload();
      } else {
        alert("Xóa bãi đỗ xe thất bại!");
      }
    });
  }
}

// Delete Extension
let currentDeleteEndpoint = "/ParkingLot/api/extensions/";
// Hiển thị modal xác nhận xóa
function showDeleteModal(id, extensionName) {
  console.log("Showing delete modal for:", id);
  currentDeleteEndpoint += id;
  document.getElementById("extensionName").textContent = extensionName;
  const deleteModal = new bootstrap.Modal(
    document.getElementById("deleteModal")
  );
  deleteModal.show();
}
function deleteExtension(endpoint = currentDeleteEndpoint) {
  console.log("Deleting extension at endpoint:", endpoint);
  if (confirm("Bạn có chắc chắn muốn xóa tiện ích này?")) {
    // API call to delete extension
    fetch(endpoint, {
      method: "DELETE",
    }).then((response) => {
      if (response.status === 204) {
        alert("Đã xóa tiện ích thành công!");
        location.reload();
      } else {
        alert("Xóa tiện ích thất bại!");
      }
    });
  }
}

// Navigation
function showActiveNavLink(event) {
  if (!event) return;

  const clickedNavLink = event.target.closest(".nav-link");
  if (!clickedNavLink) return;

  // Remove active from all
  document.querySelectorAll(".nav-link").forEach((link) => {
    link.classList.remove("active");
  });

  // Add active to clicked
  clickedNavLink.classList.add("active");
}
// Parking Lot Management
function showAddParkingLotModal() {
  document.getElementById("parkingLotForm").reset();
  document.querySelector("#parkingLotModal .modal-title").textContent =
    "Thêm Bãi đỗ xe";
  new bootstrap.Modal(document.getElementById("parkingLotModal")).show();
}

function editParkingLot(name) {
  // Load parking lot data and populate form
  console.log("Dữ liệu bãi xe:", name);
  document.querySelector("#parkingLotModal .modal-title").textContent =
    //   "Sửa Bãi đỗ xe";

    // // Sample data loading (replace with actual API call)
    // if (id === 1) {
    //   document.getElementById("lotName").value = "Bãi đỗ Trung tâm";
    //   document.getElementById("address").value = "123 Nguyễn Huệ, Q1";
    //   document.getElementById("totalSlots").value = 50;
    //   document.getElementById("pricePerHour").value = 15000;
    //   document.getElementById("description").value =
    //     "Bãi đỗ xe hiện đại tại trung tâm thành phố";
    //   document.getElementById("isActive").value = "true";
    //   // document.getElementById("wifi").checked = true;
    //   // document.getElementById("security").checked = true;
    // }

    new bootstrap.Modal(document.getElementById("parkingLotModal")).show();
}

function saveParkingLot() {
  const formData = {
    name: document.getElementById("lotName").value,
    address: document.getElementById("address").value,
    total_slots: document.getElementById("totalSlots").value,
    price_per_hour: document.getElementById("pricePerHour").value,
    description: document.getElementById("description").value,
    is_active: document.getElementById("isActive").value === "true",
    extensions: [],
  };

  // Get selected extensions
  if (document.getElementById("wifi").checked) formData.extensions.push("wifi");
  if (document.getElementById("security").checked)
    formData.extensions.push("security");
  if (document.getElementById("charging").checked)
    formData.extensions.push("charging");

  // API call to save parking lot
  console.log("Saving parking lot:", formData);

  // Close modal and refresh table
  bootstrap.Modal.getInstance(
    document.getElementById("parkingLotModal")
  ).hide();
  alert("Đã lưu bãi đỗ xe thành công!");
}

// Parking Space Management
function showAddSpaceModal() {
  document.getElementById("spaceForm").reset();
  document.querySelector("#spaceModal .modal-title").textContent =
    "Thêm Chỗ đỗ xe";
  new bootstrap.Modal(document.getElementById("spaceModal")).show();
}

function editSpace(id) {
  document.querySelector("#spaceModal .modal-title").textContent =
    "Sửa Chỗ đỗ xe";

  // Sample data loading
  if (id === 1) {
    document.getElementById("spaceName").value = "A01";
    document.getElementById("parkingLotId").value = "1";
    document.getElementById("spaceStatus").value = "available";
    document.getElementById("spaceActive").checked = true;
  }

  new bootstrap.Modal(document.getElementById("spaceModal")).show();
}

function deleteSpace(id) {
  if (confirm("Bạn có chắc chắn muốn xóa chỗ đỗ xe này?")) {
    console.log("Deleting space with ID:", id);
    alert("Đã xóa chỗ đỗ xe thành công!");
  }
}

function saveSpace() {
  const formData = {
    name: document.getElementById("spaceName").value,
    parking_lot_id: document.getElementById("parkingLotId").value,
    status: document.getElementById("spaceStatus").value,
    is_active: document.getElementById("spaceActive").checked,
  };

  console.log("Saving space:", formData);

  bootstrap.Modal.getInstance(document.getElementById("spaceModal")).hide();
  alert("Đã lưu chỗ đỗ xe thành công!");
}

function changeSpaceStatus(element, spaceId) {
  const currentClass = element.className;
  let newStatus = "available";

  if (currentClass.includes("space-available")) {
    newStatus = "booked";
    element.className = "parking-space space-booked";
  } else if (currentClass.includes("space-booked")) {
    newStatus = "occupied";
    element.className = "parking-space space-occupied";
  } else if (currentClass.includes("space-occupied")) {
    newStatus = "blocked";
    element.className = "parking-space space-blocked";
  } else if (currentClass.includes("space-blocked")) {
    newStatus = "available";
    element.className = "parking-space space-available";
  }

  // API call to update space status
  console.log(`Updating space ${spaceId} to status: ${newStatus}`);
}

function filterByParkingLot() {
  const selectedLot = event.target.value;
  console.log("Filtering by parking lot:", selectedLot);
  // Implement filtering logic
}

// Extension Management
function showAddExtensionModal() {
  document.getElementById("extensionForm").reset();
  document.querySelector("#extensionModal .modal-title").textContent =
    "Thêm Tiện ích";
  new bootstrap.Modal(document.getElementById("extensionModal")).show();
}

function editExtension(id) {
  document.querySelector("#extensionModal .modal-title").textContent =
    "Sửa Tiện ích";

  // Sample data loading
  const extensions = {
    1: "Wifi miễn phí",
    2: "Camera an ninh",
    3: "Sạc xe điện",
  };

  document.getElementById("extensionName").value = extensions[id];
  new bootstrap.Modal(document.getElementById("extensionModal")).show();
}

function saveExtension() {
  const formData = {
    name: document.getElementById("extensionName").value,
    image: document.getElementById("extensionImage").files[0],
  };

  console.log("Saving extension:", formData);

  bootstrap.Modal.getInstance(document.getElementById("extensionModal")).hide();
  alert("Đã lưu tiện ích thành công!");
}

// Initialize tooltips
document.addEventListener("DOMContentLoaded", function () {
  // Add any initialization code here
  console.log("Parking Admin Interface loaded");
});

// Sample chart data (you can replace with Chart.js or similar library)
function initCharts() {
  // This is a placeholder - implement actual charts using Chart.js
  console.log("Initializing charts...");
}

// Auto-refresh data every 30 seconds
setInterval(function () {
  // Refresh dashboard data
  console.log("Refreshing dashboard data...");
}, 30000);

// Utility functions
function formatCurrency(amount) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
}

function formatDate(date) {
  return new Intl.DateTimeFormat("vi-VN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  }).format(new Date(date));
}

// Export functions
function exportReport(type) {
  console.log(`Exporting ${type} report...`);
  // Implement export functionality
}

// Search functionality
function searchItems(query) {
  console.log("Searching for:", query);
  // Implement search logic
}

// Notification system
function showNotification(message, type = "success") {
  const notification = document.createElement("div");
  notification.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
  notification.style.cssText =
    "top: 20px; right: 20px; z-index: 9999; min-width: 300px;";
  notification.innerHTML = `
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            `;

  document.body.appendChild(notification);

  setTimeout(() => {
    notification.remove();
  }, 5000);
}

// Real-time updates (WebSocket placeholder)
function connectWebSocket() {
  // Implement WebSocket connection for real-time updates
  console.log("Connecting to WebSocket...");
}

// Form validation
function validateForm(formId) {
  const form = document.getElementById(formId);
  const inputs = form.querySelectorAll("input[required], select[required]");
  let isValid = true;

  inputs.forEach((input) => {
    if (!input.value.trim()) {
      input.classList.add("is-invalid");
      isValid = false;
    } else {
      input.classList.remove("is-invalid");
    }
  });

  return isValid;
}

// Bulk operations
function bulkUpdateSpaces() {
  const selectedSpaces = document.querySelectorAll(".space-checkbox:checked");
  if (selectedSpaces.length === 0) {
    alert("Vui lòng chọn ít nhất một chỗ đỗ xe");
    return;
  }

  const action = prompt("Nhập hành động (available/booked/occupied/blocked):");
  if (action) {
    selectedSpaces.forEach((space) => {
      // Update space status
      console.log(`Updating space ${space.value} to ${action}`);
    });
  }
}

// Data backup
function backupData() {
  console.log("Creating data backup...");
  // Implement backup functionality
}

// System maintenance
function runMaintenance() {
  if (confirm("Bạn có chắc chắn muốn chạy bảo trì hệ thống?")) {
    console.log("Running system maintenance...");
    // Implement maintenance tasks
  }
}
