package com.example.pizzeria

import android.annotation.SuppressLint
import kotlinx.coroutines.tasks.await
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzeria.ui.theme.PizzeriaTheme
import com.example.pizzeria.ui.theme.blue
import com.example.pizzeria.ui.theme.blueColor
import com.example.pizzeria.ui.theme.darkblue
import com.example.pizzeria.ui.theme.red
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

 suspend fun updateOrderStatus(orderID: String, newStatus: String) {
    val db = FirebaseFirestore.getInstance()
    val orderRef = db.collection("Order").document(orderID)

    // Sử dụng HashMap để cập nhật trạng thái mới của đơn hàng
    val updateData = hashMapOf(
        "Status" to newStatus
    )

    // Thực hiện cập nhật trạng thái đơn hàng
    orderRef.update(updateData as Map<String, Any>).await()
}

class OrderDetails : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzeriaTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = blueColor,
                                titleContentColor = Color.White,
                                navigationIconContentColor = Color.White,
                                actionIconContentColor = Color.White
                            ),
                            title = {
                                Text(
                                    text = "All Order",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    this@OrderDetails.startActivity(Intent(this@OrderDetails, MainActivity::class.java))
                                }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                                }
                            },
//                            actions = {
//                                var expanded by remember { mutableStateOf(false) }
//                                val productType = remember { mutableStateOf("") }
//                                val context = LocalContext.current
//
//                                IconButton(onClick = { expanded = true }) {
//                                    Icon(imageVector = Icons.Filled.List, contentDescription = null)
//                                }
//                            }
                        )
                    }

                    var orderList = mutableStateListOf<OrderData>()
                    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
                    val dbOrder: CollectionReference = db.collection("Order")

                    dbOrder.get().addOnSuccessListener { queryDocumentSnapshot ->
                        if (!queryDocumentSnapshot.isEmpty){
                            val list = queryDocumentSnapshot.documents
                            for (d in list){
                                val c: OrderData? = d.toObject(OrderData::class.java)
                                c?.OrderID = d.id
                                Log.e("TAG", "Course id is : " + c!!.OrderID)
                                orderList.add(c)
                            }
                        }else{
                            Toast.makeText(
                                this@OrderDetails,
                                "There is no data in the Database",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener {
                        Toast.makeText(
                            this@OrderDetails,
                            "Error while retrieving data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    OrderDetailsUI(LocalContext.current, orderList,)

                }
            }
        }
    }
}
private fun deleteDataFromFirebase(OrderID: String?, context: Context) {

    val db = FirebaseFirestore.getInstance();
    db.collection("Order").document(OrderID.toString()).delete().addOnSuccessListener {
        Toast.makeText(context, "Product removed", Toast.LENGTH_SHORT).show()
        context.startActivity(Intent(context, OrderDetails::class.java))
    }.addOnFailureListener {
        Toast.makeText(context, "Error while deleting produc", Toast.LENGTH_SHORT).show()
    }

}


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsUI(context: Context, orderList: SnapshotStateList<OrderData>) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 70.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        LazyColumn {
            itemsIndexed(orderList) { index, item ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = blue,
                    modifier = Modifier
                        .height(210.dp)
                        .padding(8.dp),
                    shadowElevation = 10.dp,
                    onClick = {

                    }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = blue,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.size(width = 100.dp, height = 100.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.order),
                                contentScale = ContentScale.Crop,
//                                contentDescription = null,
                                contentDescription = "",
                                modifier = Modifier.size(30.dp)

                                )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(2f)
                                .padding(horizontal = 15.dp, vertical = 0.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Spacer(modifier = Modifier.height(6.dp))

                            orderList[index]?.Name?.let {
                                Text(
                                    text = it,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 22.sp,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            orderList[index]?.Address?.let {
                                Text(
                                    text = it,
                                    fontSize = 22.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))

                            orderList[index]?.Total?.let {
                                Text(text = "$ " + it)
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            )
                            {
                            OutlinedButton(
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = darkblue
                                ),
                                border = BorderStroke(0.5.dp, blueColor),
                                onClick = {
                                    val i = Intent(context, Order::class.java)
                                    i.putExtra("OrderID", item?.OrderID)

                                    context.startActivity(i)
                                }
                            ) {
                                Text(
                                    text = "Detail",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            OutlinedButton(
                                shape = RoundedCornerShape(7.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black,
                                    containerColor = darkblue
                                ),
                                border = BorderStroke(0.5.dp, blueColor),
                                onClick = {
                                    coroutineScope.launch {
                                        try {
                                            // Cập nhật trạng thái đơn hàng thành "Confirmed"
                                            updateOrderStatus(
                                                orderID = item?.OrderID ?: "",
                                                newStatus = "Confirmed"
                                            )
                                            Toast.makeText(context, "Order Confirmed", Toast.LENGTH_SHORT).show()
                                        } catch (e: Exception) {
                                            Log.e("OrderDetails", "Error updating order status: ${e.message}")
                                            Toast.makeText(context, "Error updating order status", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            ) {
                                Text(
                                    text = "Confirm",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }

                    }
                }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Surface(
                            onClick = {
                                deleteDataFromFirebase(orderList[index]?.OrderID, context)
                            },
                            color = blue
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "",
                                tint = red,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(2.dp)

                            )
                        }
                    }
                }
            }
        }
    }

}

