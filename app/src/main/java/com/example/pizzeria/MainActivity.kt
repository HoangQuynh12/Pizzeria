package com.example.pizzeria

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.data.EmptyGroup.box
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.R
import com.example.pizzeria.ui.theme.Pink40
import com.example.pizzeria.ui.theme.PizzeriaTheme
import com.example.pizzeria.ui.theme.bg
import com.example.pizzeria.ui.theme.black
import com.example.pizzeria.ui.theme.blue
import com.example.pizzeria.ui.theme.blueColor


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                                    text = "ADMIN",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold
                                )
                            },

                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 70.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){

                    HomeAdminScreen(LocalContext.current,navController = rememberNavController())

                }
            }
        }
    }
}
@Composable
fun HomeAdminScreen(context: Context, navController: NavHostController){
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 10.dp, end = 10.dp),
        contentAlignment = Alignment.BottomCenter,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
//            Header()
//            Spacer(modifier = Modifier.height(30.dp))
            StatusCardLayout()
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                EachCardLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                        .weight(0.5f),
                    cardName = "Category",
                    cardName1 = "Manager",
                    cardIcon = painterResource(id = R.drawable.ic_category)
                ) {
                    context.startActivity(Intent(context, CategoryDetails::class.java))
                }

                EachCardLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                        .weight(0.5f),
                    cardName = "Rivenue",
                    cardName1 = "Manager",
                    cardIcon = painterResource(id = R.drawable.ic_money)
                ) {
                    context.startActivity(Intent(context, ProductDetails::class.java))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EachCardLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                        .weight(0.5f),
                    cardName = "Items Menu",
                    cardName1 = "Manager",
                    cardIcon = painterResource(id = R.drawable.ic_menu)
                ) {
                    context.startActivity(Intent(context, ProductDetails::class.java))
                }
                EachCardLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                        .weight(0.5f),
                    cardName = "Order",
                    cardName1 = "Manager",
                    cardIcon = painterResource(id = R.drawable.ic_order)
                ) {
                    context.startActivity(Intent(context, CategoryDetails::class.java))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                EachCardLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                        .weight(0.5f),
                    cardName = "Profile",
                    cardName1 = "Manager",
                    cardIcon = painterResource(id = R.drawable.ic_profile)
                ) {
                    context.startActivity(Intent(context, UserInformation::class.java))
                }
                EachCardLayout(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                        .weight(0.5f),
                    cardName = "User",
                    cardName1 = "Manage",
                    cardIcon = painterResource(id = R.drawable.ic_creater)
                ) {
                    context.startActivity(Intent(context, UserInformation::class.java))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(
                    modifier = Modifier
                        .clickable {
//                            context.startActivity(Intent(context, Login::class.java))
                        }
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().background(blue),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = "",
                            tint = Color.Red,
                            modifier = Modifier.size(30.dp)
                        )

                    }
                }
                context.startActivity(Intent(context, Login::class.java))
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }

}
@Composable
fun Header() {
    Row(
        modifier = Modifier
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "ADMIN", style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
        )

    }
}
@Composable
fun StatusCardLayout() {
    val box = Brush.linearGradient(
        colors = listOf(blue,blue)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(box),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardColumnContent(
                modifier = Modifier.weight(0.33f),
                icon = painterResource(id = R.drawable.ic_pending),
                centerText = "Pending Order",
                bottomText = "30",

            ) {

            }
            CardColumnContent(
                modifier = Modifier.weight(0.33f),
                icon = painterResource(id = R.drawable.ic_category),
                centerText = "Completed Order",
                bottomText = "10"
            ) {

            }
            CardColumnContent(
                modifier = Modifier.weight(0.33f),
                icon = painterResource(id = R.drawable.ic_money),
                centerText = "Revenue",
                bottomText = "$100"
            ) {

            }
        }
    }
}
@Composable
fun CardColumnContent(
    modifier: Modifier,
    icon: Painter,
    centerText: String,
    bottomText: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(15.dp)
            .width(100.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,

    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            painter = icon,
            contentDescription = "",
            tint = black
        )
        Box(modifier=Modifier.height(45.dp),

            ) {
            Text(
                text = centerText,
                color = black,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = bottomText,
            color = black,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,

                )
        )
    }
}
@Composable
fun EachCardLayout(
    modifier: Modifier = Modifier,
    cardName: String,
    cardName1: String,
    cardIcon: Painter,
    onClick: () -> Unit = {},

) {

    Card(
        modifier = modifier
            .clickable { onClick() }
            .width(200.dp)
            .height(150.dp),
        shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize() .background(blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = cardIcon,
                contentDescription = "",
                tint = black,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = cardName, style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = black,
                )
            )
            Text(
                text = cardName1, style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = black
                )
            )

        }
    }
}