# CSV Sales Data Uploader & Dynamic Summary Dashboard

A responsive web application that allows users to upload CSV files containing sales data and view comprehensive summaries in a dynamic dashboard.

## Features

### 🎯 Core Features
- **File Upload**: Drag & drop or click to upload CSV files
- **Real-time Processing**: Instant CSV parsing and data analysis
- **Dynamic Dashboard**: View all uploaded summaries with key metrics
- **Session History**: Maintain upload history across browser sessions
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile devices

### 📊 Dashboard Features
- **Upload Statistics**: Total uploads, revenue, and session count
- **Detailed Summary**: View comprehensive details for each upload
- **Sample Data Preview**: See first 5 records from each uploaded file
- **Export-Ready Data**: All processed data available for further analysis

### 🎨 User Experience
- **Modern UI**: Clean, professional design with smooth animations
- **Intuitive Navigation**: Easy switching between upload and dashboard views
- **Status Feedback**: Clear progress indicators and error messages
- **Mobile-First**: Optimized for all screen sizes# 📊 CSV Sales Data Uploader & Dynamic Summary Dashboard

A web application to upload CSV sales data and view dynamic summaries for each upload during the current server session. Built using **Spring Boot**, **HTML/CSS/JS**, and an **H2 in-memory database** for fast and temporary data storage.

---

## 🚀 Project Objective

This application allows users to:
- Upload CSV files containing  sales data.
- Process and extract key metrics like total revenue, quantity, and record count.
- View all uploaded summaries in a dashboard with modal-based detail view.
- Operate entirely in-memory, with no persistent storage, making it lightweight and fast.

---

## 🛠️ Tech Stack

### 🔧 Backend
- **Java 17**
- **Spring Boot (Maven-based)**
- **H2 In-Memory Database**
- **Spring Data JPA**
- **REST APIs**
- **Global Exception Handling**
- **DTO Mapping**

### 🎨 Frontend
- **HTML5, CSS3, JavaScript (Vanilla JS)**
- **SessionStorage for temporary session tracking**
- **Dynamic DOM manipulation**
- **AJAX (`fetch`) for API interaction**

---

## 📁 Folder Structure

```plaintext
com.sales_record.sales_record
├── advices/               → Global response and exception handling
│   ├── ApiResponse
│   ├── GlobalExceptionHandler
│   └── PaginatedResponse
│
├── config/                → Cache and mapper configuration
│   ├── CacheConfig
│   └── MapperConfig
│
├── controller/            → REST controller (API endpoints)
│   └── SalesController
│
├── dto/                   → DTOs for clean data transfer
│   ├── SalesDetailDto
│   ├── SalesRecordDto
│   └── SalesSummaryDto
│
├── entity/                → JPA entities for sales data
│   ├── SalesRecord
│   └── SalesSummary
│
├── exception/             → Custom exceptions
│   ├── FileProcessingException
│   └── InvalidFileFormatException
│
├── repository/            → Spring Data JPA repositories
│   ├── SalesRecordRepository
│   └── SalesSummaryRepository
│
├── service/               → Business logic layer
│   ├── SalesService
│   └── SalesServiceImpl
│
├── SalesRecordApplication → Main application entry point

resources/
├── static/
│   ├── index.html        ← Frontend upload UI
│   ├── script.js         ← JavaScript for interaction
│   └── styles.css        ← Simple frontend styling
├── templates/            ← (Empty / Reserved for server-side rendering if needed)
└── application.properties


├── pom.xml                ← Dependency and build requirements
```

### 🚀 Features
✅ Upload .csv files through a simple UI

✅ Parse and extract key metrics:

-> Total records
-> Total quantity sold
-> Total revenue (quantity × price per unit)

✅ In-memory storage (non-persistent)

✅ Dynamic dashboard showing:

-> Upload ID
-> Upload timestamp
-> Total revenue

✅ Click to view full summary details

✅ Global exception handling with proper API responses

✅ H2 console enabled for development/debugging


## 📄 CSV File Format

CSV files must have the following columns:

```csv
product_name,quantity,price_per_unit
Laptop,2,1200.00
Mouse,5,25.50
Keyboard,1,75.00
```

### Prerequisites
- Modern web browser (Chrome, Firefox, Safari, Edge)
- No server setup required - runs entirely in the browser
- Java 17+
- Maven 3+

### Installation Backend
```bash
git clone https://github.com/Ayaan5711/Innotek-Dashboard.git
mvn spring-boot:run
```

### Frontend
- Open 'index.html' to access the application.


### Alternative Setup (Local Server)
For better development experience, you can run a local server:

```bash
# Using Python 3
python -m http.server 8000

# Using Node.js (if you have http-server installed)
npx http-server

# Using PHP
php -S localhost:8000
```

Then visit `http://localhost:8000` in your browser.


## How to Use

### 1. Upload CSV File
1. Navigate to the Home page
2. Click "Choose CSV file" or drag and drop a CSV file
3. Review the file preview
4. Click "Upload & Process"

### 2. View Dashboard
1. After successful upload, you'll be redirected to the Dashboard
2. View upload statistics and history
3. Click "View Details" to see comprehensive information about any upload

### 3. Manage History
- View all uploaded files in the table
- Click "View Details" for detailed information
- Use "Clear History" to remove all upload records

## Technical Details

### Key Features Implementation
- **File Processing**: Client-side CSV parsing using FileReader API
- **Data Validation**: Automatic validation of CSV format and required columns
- **Error Handling**: Comprehensive error handling with user-friendly messages
- **Performance**: Efficient data processing and rendering
- **Accessibility**: Semantic HTML and keyboard navigation support

### Browser Compatibility
- Chrome 60+
- Firefox 55+
- Safari 12+
- Edge 79+

## Data Processing

The application processes CSV data to calculate:
- **Total Records**: Number of rows in the CSV file
- **Total Quantity**: Sum of all quantity values
- **Total Revenue**: Sum of (quantity × price) for all records
- **Sample Data**: First 5 records for preview


## Troubleshooting

### Common Issues

**File not uploading**
- Ensure the file is a valid CSV format
- Check that required columns are present
- Verify file size is reasonable (< 10MB recommended)

**Data not displaying correctly**
- Check CSV format matches expected structure
- Ensure numeric columns contain valid numbers
- Verify date format is consistent

**Dashboard not updating**
- Refresh the page to reload from localStorage
- Check browser console for JavaScript errors
- Clear browser cache if needed

### Browser Console Errors
- Check for JavaScript errors in browser developer tools
- Verify all files are loaded correctly
- Ensure localStorage is enabled in your browser

![Dashboard](https://github.com/Ayaan5711/Innotek-Dashboard/blob/main/images/1.png)
![Dashboard](https://github.com/Ayaan5711/Innotek-Dashboard/blob/main/images/4.png)
![Dashboard](https://github.com/Ayaan5711/Innotek-Dashboard/blob/main/images/3.png)
![Dashboard](https://github.com/Ayaan5711/Innotek-Dashboard/blob/main/images/6.png)
## 🔮 Future Scope

- 💾 **Persistent Database Integration**: Replace in-memory storage with a persistent database like PostgreSQL or MySQL to retain summaries across restarts.
- 📊 **Advanced Data Visualization**: Integrate charting libraries (e.g., Chart.js or D3.js) for graphical insights into sales trends.
- 🧾 **Export Feature**: Allow users to download summary reports in PDF or Excel format.
- 🔐 **Authentication & Authorization**: Secure upload and summary views with user login and roles.
- 📂 **Multi-File Upload**: Enable batch uploads and queue processing for multiple CSV files.
- 🌐 **Cloud Deployment**: Host the application on platforms like AWS/GCP/Azure with autoscaling and database backups.
- 📈 **Analytics Dashboard**: Provide insights like top-selling products, revenue trends, and quantity movement over time.
- 🧠 **AI Forecasting (Long Term)**: Predict future sales using machine learning models based on historical uploads.

---

## 👤 Author

**Ayaan Ahmed**   
📧ahmedayaan570@gmail.com
