// API Configuration
const API_BASE_URL = "http://localhost:8080/api/v1/sales"



// Global variables
let salesSummaries = []
let sessionUploads = 0

// DOM elements
const homeLink = document.getElementById("home-link")
const dashboardLink = document.getElementById("dashboard-link")
const homePage = document.getElementById("home-page")
const dashboardPage = document.getElementById("dashboard-page")
const uploadForm = document.getElementById("upload-form")
const csvFileInput = document.getElementById("csv-file")
const filePreview = document.getElementById("file-preview")
const fileName = document.getElementById("file-name")
const fileSize = document.getElementById("file-size")
const removeFileBtn = document.getElementById("remove-file")
const uploadBtn = document.getElementById("upload-btn")
const uploadStatus = document.getElementById("upload-status")
const statusIcon = document.getElementById("status-icon")
const statusTitle = document.getElementById("status-title")
const statusMessage = document.getElementById("status-message")
const progressBar = document.getElementById("progress-bar")
const progressFill = document.getElementById("progress-fill")
const uploadsTable = document.getElementById("uploads-table")
const uploadsTbody = document.getElementById("uploads-tbody")
const emptyState = document.getElementById("empty-state")
const totalUploadsEl = document.getElementById("total-uploads")
const totalRevenueEl = document.getElementById("total-revenue")
const sessionUploadsEl = document.getElementById("session-uploads")
const refreshDataBtn = document.getElementById("refresh-data")
const dashboardLoading = document.getElementById("dashboard-loading")
const summaryModal = document.getElementById("summary-modal")
const modalClose = document.getElementById("modal-close")
const modalBody = document.getElementById("modal-body")
const modalLoading = document.getElementById("modal-loading")
const toast = document.getElementById("toast")
const toastIcon = document.getElementById("toast-icon")
const toastMessage = document.getElementById("toast-message")

// Initialize application
document.addEventListener("DOMContentLoaded", () => {
  loadSessionData()
  setupEventListeners()
})

// Event listeners setup
function setupEventListeners() {
  // Navigation
  homeLink.addEventListener("click", (e) => {
    e.preventDefault()
    showHomePage()
  })
  dashboardLink.addEventListener("click", (e) => {
    e.preventDefault()
    showDashboardPage()
  })

  // File handling
  csvFileInput.addEventListener("change", handleFileSelect)
  removeFileBtn.addEventListener("click", removeSelectedFile)

  // Form submission
  uploadForm.addEventListener("submit", handleFileUpload)

  // Dashboard actions
  refreshDataBtn.addEventListener("click", refreshDashboardData)

  // Modal
  modalClose.addEventListener("click", closeModal)
  summaryModal.addEventListener("click", (e) => {
    if (e.target === summaryModal) {
      closeModal()
    }
  })

  // Drag and drop
  setupDragAndDrop()
}

// Navigation functions
function showHomePage() {
  homePage.classList.add("active")
  dashboardPage.classList.remove("active")
  homeLink.classList.add("active")
  dashboardLink.classList.remove("active")
}

function showDashboardPage() {
  dashboardPage.classList.add("active")
  homePage.classList.remove("active")
  dashboardLink.classList.add("active")
  homeLink.classList.remove("active")

  // Load dashboard data when switching to dashboard
  loadDashboardData()
}

// File handling functions
function handleFileSelect(event) {
  const file = event.target.files[0]
  if (file) {
    if (file.type === "text/csv" || file.name.endsWith(".csv")) {
      displayFilePreview(file)
      uploadBtn.disabled = false
    } else {
      showToast("Please select a valid CSV file.", "error")
      resetFileInput()
    }
  }
}

function displayFilePreview(file) {
  fileName.textContent = file.name
  fileSize.textContent = formatFileSize(file.size)
  filePreview.style.display = "block"
}

function formatFileSize(bytes) {
  if (bytes === 0) return "0 Bytes"
  const k = 1024
  const sizes = ["Bytes", "KB", "MB", "GB"]
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return Number.parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + " " + sizes[i]
}

function removeSelectedFile() {
  resetFileInput()
}

function resetFileInput() {
  csvFileInput.value = ""
  filePreview.style.display = "none"
  uploadBtn.disabled = true
}

// Drag and drop functionality
function setupDragAndDrop() {
  const fileLabel = document.querySelector(".file-label")
  ;["dragenter", "dragover", "dragleave", "drop"].forEach((eventName) => {
    fileLabel.addEventListener(eventName, preventDefaults, false)
  })

  function preventDefaults(e) {
    e.preventDefault()
    e.stopPropagation()
  }
  ;["dragenter", "dragover"].forEach((eventName) => {
    fileLabel.addEventListener(eventName, highlight, false)
  })
  ;["dragleave", "drop"].forEach((eventName) => {
    fileLabel.addEventListener(eventName, unhighlight, false)
  })

  function highlight(e) {
    fileLabel.style.borderColor = "#667eea"
    fileLabel.style.background = "#f0f4ff"
  }

  function unhighlight(e) {
    fileLabel.style.borderColor = "#ddd"
    fileLabel.style.background = "#f8f9fa"
  }

  fileLabel.addEventListener("drop", handleDrop, false)

  function handleDrop(e) {
    const dt = e.dataTransfer
    const files = dt.files

    if (files.length > 0) {
      const file = files[0]
      if (file.type === "text/csv" || file.name.endsWith(".csv")) {
        csvFileInput.files = files
        displayFilePreview(file)
        uploadBtn.disabled = false
      } else {
        showToast("Please drop a valid CSV file.", "error")
      }
    }
  }
}

// File upload handling
async function handleFileUpload(event) {
  event.preventDefault()

  const file = csvFileInput.files[0]
  if (!file) {
    showToast("Please select a file to upload.", "error")
    return
  }

  showUploadStatus("Processing...", "Please wait while we process your file", "spinner")
  showProgressBar()

  try {
    const formData = new FormData()
    formData.append("file", file)

    // Simulate progress
    animateProgress()

    const response = await fetch(`${API_BASE_URL}/upload-sales-data`, {
      method: "POST",
      body: formData,
    })

    const result = await response.json()

    if (result.status === "success") {
      sessionUploads++

      // Store the returned summary data
      const summaryData = result.data
      const existingSummaries = JSON.parse(sessionStorage.getItem("salesSummaries") || "[]")
      existingSummaries.unshift(summaryData) // Add new summary at the beginning
      sessionStorage.setItem("salesSummaries", JSON.stringify(existingSummaries))

      saveSessionData()

      showUploadStatus("Success!", "File processed successfully", "check")
      showToast("File uploaded and processed successfully!", "success")

      setTimeout(() => {
        showDashboardPage()
        resetFileInput()
        hideUploadStatus()
        hideProgressBar()
      }, 2000)
    } else {
      throw new Error(result.message || "Upload failed")
    }
  } catch (error) {
    console.error("Error uploading file:", error)
    showUploadStatus("Error", error.message || "Failed to process the file. Please try again.", "times")
    showToast(error.message || "Upload failed. Please try again.", "error")

    setTimeout(() => {
      hideUploadStatus()
      hideProgressBar()
    }, 3000)
  }
}

// Progress bar functions
function showProgressBar() {
  progressBar.style.display = "block"
  progressFill.style.width = "0%"
}

function hideProgressBar() {
  progressBar.style.display = "none"
}

function animateProgress() {
  let progress = 0
  const interval = setInterval(() => {
    progress += Math.random() * 15
    if (progress > 90) {
      progress = 90
      clearInterval(interval)
    }
    progressFill.style.width = progress + "%"
  }, 200)

  // Complete progress when upload is done
  setTimeout(() => {
    clearInterval(interval)
    progressFill.style.width = "100%"
  }, 2000)
}

// Status management
function showUploadStatus(title, message, iconType) {
  statusTitle.textContent = title
  statusMessage.textContent = message

  statusIcon.className = "fas"
  if (iconType === "spinner") {
    statusIcon.classList.add("fa-spinner", "fa-spin")
    statusIcon.style.color = "#667eea"
  } else if (iconType === "check") {
    statusIcon.classList.add("fa-check-circle")
    statusIcon.style.color = "#28a745"
  } else if (iconType === "times") {
    statusIcon.classList.add("fa-times-circle")
    statusIcon.style.color = "#dc3545"
  }

  uploadStatus.style.display = "block"
}

function hideUploadStatus() {
  uploadStatus.style.display = "none"
}

// Dashboard functions
async function loadDashboardData() {
  showDashboardLoading()

  try {
    // First try to fetch from API (you'll need to add this endpoint)
    try {
      const response = await fetch(`${API_BASE_URL}/sales-summaries`)
      const result = await response.json()

      if (result.status === "success") {
        salesSummaries = result.data || []
      } else {
        throw new Error("API fetch failed")
      }
    } catch (apiError) {
      console.log("API endpoint not available, using session storage")
      // Fallback to session storage
      const sessionSummaries = JSON.parse(sessionStorage.getItem("salesSummaries") || "[]")
      salesSummaries = sessionSummaries
    }

    updateDashboardStats()
    renderUploadsTable()
  } catch (error) {
    console.error("Error loading dashboard data:", error)
    showToast("Failed to load dashboard data", "error")
  } finally {
    hideDashboardLoading()
  }
}

async function refreshDashboardData() {
  await loadDashboardData()
  showToast("Dashboard data refreshed", "success")
}

function showDashboardLoading() {
  dashboardLoading.style.display = "flex"
}

function hideDashboardLoading() {
  dashboardLoading.style.display = "none"
}

function updateDashboardStats() {
  const totalRevenue = salesSummaries.reduce((sum, summary) => sum + (summary.totalRevenue || 0), 0)

  // Animate numbers
  animateNumber(totalUploadsEl, salesSummaries.length)
  animateNumber(totalRevenueEl, totalRevenue, true)
  animateNumber(sessionUploadsEl, sessionUploads)
}

function animateNumber(element, targetValue, isCurrency = false) {
  const startValue = 0
  const duration = 1000
  const startTime = performance.now()

  function updateNumber(currentTime) {
    const elapsed = currentTime - startTime
    const progress = Math.min(elapsed / duration, 1)

    const currentValue = startValue + (targetValue - startValue) * progress

    if (isCurrency) {
      element.textContent = formatCurrency(currentValue)
    } else {
      element.textContent = Math.floor(currentValue).toLocaleString()
    }

    if (progress < 1) {
      requestAnimationFrame(updateNumber)
    }
  }

  requestAnimationFrame(updateNumber)
}

function renderUploadsTable() {
  if (salesSummaries.length === 0) {
    uploadsTable.style.display = "none"
    emptyState.style.display = "block"
    return
  }

  uploadsTable.style.display = "table"
  emptyState.style.display = "none"

  uploadsTbody.innerHTML = ""

  salesSummaries.forEach((summary, index) => {
    const row = document.createElement("tr")
    row.style.animationDelay = `${index * 0.1}s`
    row.classList.add("fade-in-row")

    row.innerHTML = `
            <td><strong>${summary.id || "N/A"}</strong></td>
            <td>${formatDateTime(summary.uploadTimestamp)}</td>
            <td><strong>${formatCurrency(summary.totalRevenue || 0)}</strong></td>
            <td>${(summary.totalRecords || 0).toLocaleString()}</td>
            <td>${(summary.totalQuantity || 0).toLocaleString()}</td>
            <td>
                <button class="btn btn-success" onclick="showSalesDetails(${summary.id})" ${!summary.id ? "disabled" : ""}>
                    <i class="fas fa-eye"></i>
                    View Details
                </button>
            </td>
        `
    uploadsTbody.appendChild(row)
  })
}

// Sales details modal
async function showSalesDetails(summaryId) {
  showModalLoading()
  summaryModal.style.display = "block"

  try {
    const response = await fetch(`${API_BASE_URL}/sales-summaries/${summaryId}`)
    const result = await response.json()

    if (result.status === "success") {
      displaySalesDetails(result.data)
    } else {
      throw new Error(result.message || "Failed to load sales details")
    }
  } catch (error) {
    console.error("Error loading sales details:", error)
    modalBody.innerHTML = `
            <div style="text-align: center; padding: 40px; color: #dc3545;">
                <i class="fas fa-exclamation-triangle" style="font-size: 3rem; margin-bottom: 20px;"></i>
                <h3>Error Loading Details</h3>
                <p>${error.message}</p>
            </div>
        `
    showToast("Failed to load sales details", "error")
  } finally {
    hideModalLoading()
  }
}

function displaySalesDetails(salesDetail) {
  const modalContent = `
        <div class="summary-details">
            <div class="summary-item">
                <span class="summary-label">Summary ID</span>
                <span class="summary-value">${salesDetail.summaryId}</span>
            </div>
            <div class="summary-item">
                <span class="summary-label">Upload Timestamp</span>
                <span class="summary-value">${formatDateTime(salesDetail.uploadTimestamp)}</span>
            </div>
            <div class="summary-item">
                <span class="summary-label">Total Records</span>
                <span class="summary-value">${salesDetail.totalRecords.toLocaleString()}</span>
            </div>
            <div class="summary-item">
                <span class="summary-label">Total Quantity</span>
                <span class="summary-value">${salesDetail.totalQuantity.toLocaleString()}</span>
            </div>
            <div class="summary-item">
                <span class="summary-label">Total Revenue</span>
                <span class="summary-value">${formatCurrency(salesDetail.totalRevenue)}</span>
            </div>
        </div>

        ${
          salesDetail.records && salesDetail.records.length > 0
            ? `
        <div style="margin-top: 30px;">
            <h3 style="margin-bottom: 15px; color: #333;">Sales Records</h3>
            <div style="overflow-x: auto;">
                <table class="records-table">
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price Per Unit</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${salesDetail.records
                          .map(
                            (record) => `
                            <tr>
                                <td>${record.productName}</td>
                                <td>${record.quantity.toLocaleString()}</td>
                                <td>${formatCurrency(record.pricePerUnit)}</td>
                                <td><strong>${formatCurrency(record.quantity * record.pricePerUnit)}</strong></td>
                            </tr>
                        `,
                          )
                          .join("")}
                    </tbody>
                </table>
            </div>
        </div>
        `
            : ""
        }
    `

  modalBody.innerHTML = modalContent
}

function showModalLoading() {
  modalLoading.style.display = "block"
  modalBody.style.display = "none"
}

function hideModalLoading() {
  modalLoading.style.display = "none"
  modalBody.style.display = "block"
}

function closeModal() {
  summaryModal.style.display = "none"
}

// Toast notification
function showToast(message, type = "success") {
  toastMessage.textContent = message
  toast.className = `toast ${type}`

  if (type === "success") {
    toastIcon.className = "fas fa-check-circle toast-icon"
  } else if (type === "error") {
    toastIcon.className = "fas fa-exclamation-circle toast-icon"
  }

  toast.classList.add("show")

  setTimeout(() => {
    toast.classList.remove("show")
  }, 4000)
}

// Utility functions
function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  }).format(amount)
}

function formatDateTime(dateString) {
  const date = new Date(dateString)
  return date.toLocaleString("en-US", {
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  })
}

// Session storage functions
function saveSessionData() {
  try {
    sessionStorage.setItem("sessionUploads", sessionUploads.toString())
  } catch (error) {
    console.error("Error saving session data:", error)
  }
}

function loadSessionData() {
  try {
    const savedSessionUploads = sessionStorage.getItem("sessionUploads")
    if (savedSessionUploads) {
      sessionUploads = Number.parseInt(savedSessionUploads)
    }
  } catch (error) {
    console.error("Error loading session data:", error)
    sessionUploads = 0
  }
}

// Global functions for HTML onclick handlers
window.showHomePage = showHomePage
window.showSalesDetails = showSalesDetails

// Add CSS animation for table rows
const style = document.createElement("style")
style.textContent = `
    .fade-in-row {
        opacity: 0;
        transform: translateY(20px);
        animation: fadeInRow 0.5s ease forwards;
    }

    @keyframes fadeInRow {
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
`
document.head.appendChild(style)
