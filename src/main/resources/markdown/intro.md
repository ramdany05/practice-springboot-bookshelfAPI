# API Introduction

Welcome to the **Bookshelf API** documentation!

## Overview

This API allows you to manage a collection of books with the following features:

- **Add new books** to your bookshelf
- **View book details** including author, year, and summary
- **Update book information** such as read progress
- **Delete books** from your collection
- **Search and filter** books by various criteria

## Key Features

### Book Management
- Create, Read, Update, and Delete (CRUD) operations
- Track reading progress (pages read vs total pages)
- Mark books as currently reading or finished

### Filtering Options
- Filter by book name (case-insensitive search)
- Filter by reading status
- Filter by completion status

## Getting Started

To start using the API, you can:

1. Add a new book using the POST `/books` endpoint
2. View all books with GET `/books`
3. Get details of a specific book with GET `/books/{id}`
4. Update book information with PUT `/books/{id}`
5. Delete a book with DELETE `/books/{id}`

## Data Model

Each book contains the following information:
- **id**: Unique identifier
- **name**: Book title (required)
- **year**: Publication year
- **author**: Author name
- **summary**: Book description
- **publisher**: Publishing company
- **pageCount**: Total number of pages
- **readPage**: Number of pages read
- **finished**: Auto-calculated based on readPage and pageCount
- **reading**: Whether currently reading the book
- **createdAt**: Timestamp when book was added
- **updateAt**: Timestamp when book was last updated

For detailed endpoint information, please refer to the main README.md file.
