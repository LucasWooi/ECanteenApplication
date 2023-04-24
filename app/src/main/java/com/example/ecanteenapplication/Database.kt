package com.example.ecanteenapplication

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.lang.Double.parseDouble
import java.lang.Integer.parseInt

class Database {
    companion object{
        val db = Firebase.firestore
        var students: MutableList<Students> = mutableListOf()
        var restaurants: MutableList<Restaurant> = mutableListOf()
        var admins: MutableList<Admin> = mutableListOf()
        var foods: MutableList<Food> = mutableListOf()
        var orders: MutableList<Order> = mutableListOf()

        fun getAllDataFromFirestore(){
            db.collection("Student")
                .get()
                .addOnSuccessListener { studentDatabase ->
                    for(student in studentDatabase){
                        val studentData = Students(student.data["StudentID"].toString(),
                                            student.data["StudName"].toString(),
                                            student.data["Email"].toString(),
                                            student.data["PhoneNumber"].toString(),
                                            parseDouble(student.data["WalletBalance"].toString()),
                                            student.data["Password"].toString(),
                                            student.data["ConfirmPass"].toString())
                        students.add(studentData)
                    }
                }

            db.collection("Restaurant")
                .get()
                .addOnSuccessListener { restaurantDatabase ->
                    for(restaurant in restaurantDatabase){
                        val restaurantData = Restaurant(restaurant.data["RestaurantID"].toString(),
                            restaurant.data["RestaurantName"].toString(),
                            restaurant.data["Email"].toString(),
                            restaurant.data["OwnerName"].toString(),
                            restaurant.data["PhoneNumber"].toString(),
                            parseDouble(restaurant.data["WalletBalance"].toString()),
                            restaurant.data["Password"].toString(),
                            restaurant.data["ConfirmPass"].toString())
                        restaurants.add(restaurantData)
                    }
                }

            db.collection("Admin")
                .get()
                .addOnSuccessListener { adminDatabase ->
                    for(admin in adminDatabase){
                        val adminData = Admin(admin.data["AdminID"].toString(),
                            admin.data["AdminName"].toString(),
                            admin.data["Email"].toString(),
                            admin.data["PhoneNumber"].toString(),
                            parseDouble(admin.data["WalletBalance"].toString()),
                            admin.data["Password"].toString(),
                            admin.data["ConfirmPass"].toString())
                        admins.add(adminData)
                    }
                }

            db.collection("Food")
                .get()
                .addOnSuccessListener { foodDatabase ->
                    for(food in foodDatabase){
                        val foodData = Food(food.data["FoodID"].toString(),
                            food.data["FoodName"].toString(),
                            parseDouble(food.data["FoodPrice"].toString()),
                            food.data["RestaurantID"].toString())
                        foods.add(foodData)
                    }
                }

            db.collection("Order")
                .get()
                .addOnSuccessListener { orderDatabase ->
                    for(order in orderDatabase){
                        val orderData = Order(order.data["OrderID"].toString(),
                            order.data["FoodID"].toString(),
                            order.data["FoodName"].toString(),
                            parseDouble(order.data["FoodPrice"].toString()),
                            parseInt(order.data["OrderQty"].toString()),
                            order.data["ResID"].toString(),
                            order.data["StudID"].toString(),
                            parseDouble(order.data["TotalPay"].toString()))
                        orders.add(orderData)
                    }
                }
        }

        fun addStudent(studID:String, studName:String, studEmail:String, studPhone:String,
                       studBalance:Double, studPass:String, studConfirmPass:String){
            var newStudent = Students(studID,studName,studEmail,studPhone,studBalance,studPass,studConfirmPass)
            students.add(newStudent)

            val addNewData = hashMapOf(
                "StudentID" to studID,
                "StudName" to studName,
                "Email" to studEmail,
                "PhoneNumber" to studPhone,
                "WalletBalance" to studBalance,
                "Password" to studPass,
                "ConfirmPass" to studConfirmPass
            )

            db.collection("Student").document(studID)
                .set(addNewData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot added successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error add new data",it)
                }
        }

        fun addRestaurant(resID:String, resName:String, ownerName:String, resEmail:String, resPhone:String,
                       resBalance:Double, resPass:String, resConfirmPass:String){
            var newRestaurant = Restaurant(resID,resName,ownerName, resEmail,resPhone,resBalance,resPass,resConfirmPass)
            restaurants.add(newRestaurant)

            val addNewData = hashMapOf(
                "RestaurantID" to resID,
                "RestaurantName" to resName,
                "OwnerName" to ownerName,
                "Email" to resEmail,
                "PhoneNumber" to resPhone,
                "WalletBalance" to resBalance,
                "Password" to resPass,
                "ConfirmPass" to resConfirmPass
            )

            db.collection("Restaurant").document(resID)
                .set(addNewData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot added successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error add new data",it)
                }
        }

        fun addFood(foodID:String, foodName:String, foodPrice:Double, resID:String){
            var addNewFood = Food(foodID,foodName,foodPrice,resID)
            foods.add(addNewFood)

            val addFoodData = hashMapOf(
                "FoodID" to foodID,
                "FoodName" to foodName,
                "FoodPrice" to foodPrice,
                "RestaurantID" to resID
            )

            db.collection("Food").document(foodID)
                .set(addFoodData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot updated successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error update data",it)
                }
        }

        fun addOrder(orderID:String, foodID: String, foodName:String, foodPrice:Double, foodQty:Int,
                     resID:String, studID: String, payment: Double){
            var addNewOrder = Order(orderID,foodID,foodName,foodPrice,foodQty,resID,studID,payment)
            orders.add(addNewOrder)

            val addOrderData = hashMapOf(
                "OrderID" to orderID,
                "FoodID" to foodID,
                "FoodName" to foodName,
                "FoodPrice" to foodPrice,
                "OrderQty" to foodQty,
                "ResID" to resID,
                "StudID" to studID,
                "TotalPay" to payment
            )

            db.collection("Order").document(orderID)
                .set(addOrderData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot updated successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error update data",it)
                }
        }

        fun updateStudent(studID:String, studName:String, studEmail:String, studPhone:String,
                          studBalance:Double, studPass:String, studConfirmPass:String,getPosition:Int){
            var updatedStudent = Students(studID,studName,studEmail,studPhone,studBalance,studPass,studConfirmPass)
            students.set(getPosition,updatedStudent)

            val updateStudentData = mapOf(
                "StudentID" to studID,
                "StudName" to studName,
                "Email" to studEmail,
                "PhoneNumber" to studPhone,
                "WalletBalance" to studBalance,
                "Password" to studPass,
                "ConfirmPass" to studConfirmPass
            )

            db.collection("Student").document(studID)
                .update(updateStudentData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot updated successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error update data",it)
                }
        }

        fun updateRestaurant(resID:String, resName:String, ownerName:String, resEmail:String, resPhone:String,
                             resBalance:Double, resPass:String, resConfirmPass:String){
            var updatedRestaurant = Restaurant(resID,resName,ownerName, resEmail,resPhone,resBalance,resPass,resConfirmPass)
            restaurants.add(updatedRestaurant)

            val updateRestaurantData = mapOf(
                "RestaurantID" to resID,
                "RestaurantName" to resName,
                "OwnerName" to ownerName,
                "Email" to resEmail,
                "PhoneNumber" to resPhone,
                "WalletBalance" to resBalance,
                "Password" to resPass,
                "ConfirmPass" to resConfirmPass
            )

            db.collection("Restaurant").document(resID)
                .update(updateRestaurantData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot updated successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error update data",it)
                }
        }

        fun updateFood(foodID:String, foodName:String, foodPrice:Double, resID:String){
            var updatedFood = Food(foodID,foodName,foodPrice,resID)
            foods.add(updatedFood)

            val updateFoodData = mapOf(
                "FoodID" to foodID,
                "FoodName" to foodName,
                "FoodPrice" to foodPrice,
                "RestaurantID" to resID
            )

            db.collection("Food").document(foodID)
                .update(updateFoodData)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot updated successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error update data",it)
                }
        }

        fun deleteStudent(studID: String){
            db.collection("Student").document(studID)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot deleted successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error delete data",it)
                }
        }

        fun deleteRestaurant(resID: String){
            db.collection("Restaurant").document(resID)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot deleted successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error delete data",it)
                }
        }

        fun deleteFood(foodID: String){
            db.collection("Food").document(foodID)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot deleted successfully!")
                }
                .addOnFailureListener {
                    Log.d(TAG,"Error delete data",it)
                }
        }
    }
}

class Students{
    private var studID:String
    private var studName:String
    private var email:String
    private var phoneNumber:String
    private var walletBalance:Double
    private var password:String
    private var confirmPass:String

    constructor(){
        studID = ""
        studName = ""
        email = ""
        phoneNumber = ""
        walletBalance = 0.0
        password = ""
        confirmPass = ""
    }

    constructor(StudID:String, StudName:String, StudEmail:String, StudPhoneNumber:String,
                StudBalance:Double, StudPassword:String, StudConfirmPass:String){
        studID = StudID
        studName = StudName
        email = StudEmail
        phoneNumber = StudPhoneNumber
        walletBalance = StudBalance
        password = StudPassword
        confirmPass = StudConfirmPass
    }

    fun getID():String{
        return studID
    }

    fun getName():String{
        return studName
    }

    fun getEmail():String{
        return email
    }

    fun getPhoneNumber():String{
        return phoneNumber
    }

    fun getWalletBalance():Double{
        return walletBalance
    }

    fun getPassword():String{
        return password
    }

    fun getConfirmPass():String{
        return confirmPass
    }
}

class Restaurant{
    private var resID:String
    private var resName:String
    private var ownerName:String
    private var email:String
    private var phoneNumber:String
    private var walletBalance:Double
    private var password:String
    private var confirmPass:String

    constructor(){
        resID = ""
        resName = ""
        ownerName = ""
        email = ""
        phoneNumber = ""
        walletBalance = 0.0
        password = ""
        confirmPass = ""
    }

    constructor(ResID:String, ResName:String, OwnerName:String, ResEmail:String,
                ResPhoneNumber:String, ResBalance:Double, ResPassword:String, ResConfirmPass:String){
        resID = ResID
        resName = ResName
        ownerName = OwnerName
        email = ResEmail
        phoneNumber = ResPhoneNumber
        walletBalance = ResBalance
        password = ResPassword
        confirmPass = ResConfirmPass
    }

    fun getID():String{
        return resID
    }

    fun getName():String{
        return resName
    }

    fun getOwnerName():String{
        return ownerName
    }

    fun getEmail():String{
        return email
    }

    fun getPhoneNumber():String{
        return phoneNumber
    }

    fun getWalletBalance():Double{
        return walletBalance
    }

    fun getPassword():String{
        return password
    }

    fun getConfirmPass():String{
        return confirmPass
    }
}

class Admin{
    private var adminID:String
    private var adminName:String
    private var email:String
    private var phoneNumber:String
    private var walletBalance:Double
    private var password:String
    private var confirmPass:String

    constructor(){
        adminID = ""
        adminName = ""
        email = ""
        phoneNumber = ""
        walletBalance = 0.0
        password = ""
        confirmPass = ""
    }

    constructor(AdminID:String, AdminName:String, AdminEmail:String, AdminPhoneNumber:String,
                AdminBalance:Double, AdminPassword:String, AdminConfirmPass:String){
        adminID = AdminID
        adminName = AdminName
        email = AdminEmail
        phoneNumber = AdminPhoneNumber
        walletBalance = AdminBalance
        password = AdminPassword
        confirmPass = AdminConfirmPass
    }

    fun getID():String{
        return adminID
    }

    fun getName():String{
        return adminName
    }

    fun getEmail():String{
        return email
    }

    fun getPhoneNumber():String{
        return phoneNumber
    }

    fun getWalletBalance():Double{
        return walletBalance
    }

    fun getPassword():String{
        return password
    }

    fun getConfirmPass():String{
        return confirmPass
    }
}

class Food{
    private var foodID:String
    private var foodName:String
    private var foodPrice:Double
    private var resID:String

    constructor(){
        foodID = ""
        foodName = ""
        foodPrice = 0.0
        resID = ""
    }

    constructor(FoodID:String, FoodName:String, FoodPrice:Double, ResID:String){
        foodID = FoodID
        foodName = FoodName
        foodPrice = FoodPrice
        resID = ResID
    }

    fun getID():String{
        return foodID
    }

    fun getName():String{
        return foodName
    }

    fun getPrice():Double{
        return foodPrice
    }

    fun getResID():String{
        return resID
    }
}

class Order{
    private var orderID:String
    private var foodID:String
    private var foodName:String
    private var foodPrice:Double
    private var foodQty:Int
    private var resID:String
    private var studID:String
    private var payment: Double

    constructor(){
        orderID = ""
        foodID = ""
        foodName = ""
        foodPrice = 0.0
        foodQty = 0
        resID = ""
        studID = ""
        payment = 0.0
    }

    constructor(OrderID:String, FoodID: String, FoodName:String, FoodPrice:Double,
                FoodQty:Int, ResID:String, StudID:String, Payment:Double){
        orderID = OrderID
        foodID = FoodID
        foodName = FoodName
        foodPrice = FoodPrice
        foodQty = FoodQty
        resID = ResID
        studID = StudID
        payment = Payment

    }

    fun getID():String{
        return orderID
    }

    fun getFoodID():String{
        return foodID
    }

    fun getName():String{
        return foodName
    }

    fun getPrice():Double{
        return foodPrice
    }

    fun getQty():Int{
        return foodQty
    }

    fun getResID():String{
        return resID
    }

    fun getStudID():String{
        return studID
    }

    fun getPayment(): Double{
        return payment
    }
}