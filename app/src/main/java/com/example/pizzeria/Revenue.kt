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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class Revenue : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzeriaTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
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
                                    text = "Revenue",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    this@Revenue.startActivity(Intent(this@Revenue, MainActivity::class.java))
                                }) {
                                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                                }
                            },
                        )
                    }

                    var orderList = remember { mutableStateListOf<OrderData>() }
                    var totalConfirmed = remember { mutableStateOf(0.0) }
                    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
                    val dbOrder: CollectionReference = db.collection("Order")

                    LaunchedEffect(Unit) {
                        val confirmedOrders = dbOrder.whereEqualTo("Status", "Confirmed").get().await()
                        var total = 0.0
                        for (document in confirmedOrders) {
                            val order = document.toObject(OrderData::class.java)
                            order.OrderID = document.id
                            orderList.add(order)
                            total += order.Total ?: 0.0
                        }
                        totalConfirmed.value = total
                    }

                    RevenueUI(LocalContext.current, orderList, totalConfirmed.value)
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueUI(context: Context, orderList: SnapshotStateList<OrderData>, totalConfirmed: Double) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 70.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Revenue: $$totalConfirmed",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )

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
                        val i = Intent(context, Order::class.java)
                        i.putExtra("productID", item?.OrderID)
                        context.startActivity(i)
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

                            item.Name?.let {
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

                            item.Address?.let {
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

                            item.Total?.let {
                                Text(text = "$ $it")
                            }

                            Spacer(modifier = Modifier.height(3.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
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
                            }
                        }
                    }
                }
            }
        }
    }
}
