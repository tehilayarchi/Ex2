Ex2 - Foundation of Object-Oriented and Recursion

## 📝 General Description
This project focuses on designing and implementing a basic spreadsheet, illustrating object-oriented programming (OOP) principles. The spreadsheet is represented as a two-dimensional table (2D array), where each cell can contain one of the following:
- **Text (String)**
- **Number (Double)**
- **Formula (Formula)**, dynamically calculated.

### Supported Formula Types:
1. `=number` – A direct numeric value.
2. `=(Formula)` – A nested formula.
3. `=Formula op Formula` – A formula combined with another formula using one of the operators `{+, -, *, /}`.
4. `=cell` – References another cell (e.g., A0, B1, C12).
---

##  Purpose
The goal of the project is to implement the functionality of a spreadsheet, enabling users to:
1. Input, view, and edit cell values.
2. Dynamically calculate formulas within cells.
3. Handle inter-cell dependencies (e.g., formulas referencing other cells).

---
## 🛠 Project Structure
The project is divided into the following key components:
SCell: Represents an individual cell in the spreadsheet, capable of holding text, numbers, or formulas.
Ex2Sheet: The main spreadsheet class, managing a 2D array of cells and handling operations like setting and retrieving cell values.
Ex2Utils: Utility class providing helper methods for initializing or resetting spreadsheets.
Ex2Test: Test suite for verifying the functionality and correctness of the spreadsheet implementation.
*Each class has a text file named after it that tests it
![image](https://github.com/user-attachments/assets/94c55be6-ab0d-4118-b565-9b033cdbef68)

