package akram.bensalem.powersh.ui.main.screens

import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.components.CollapsingToolbarBase
import akram.bensalem.powersh.ui.theme.Dimens
import akram.bensalem.powersh.ui.theme.PowerSHTheme
import akram.bensalem.powersh.utils.authentification.Authentifier
import android.app.Activity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DoorBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.SecurityUpdate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun profileScreen(
    modifier: Modifier = Modifier,
    authentification : Authentifier = Authentifier(LocalContext.current as Activity),
    listState: LazyListState = rememberLazyListState(),
    onEditeClicked: () -> Unit = {},
    onBackButtonPressed: () -> Unit = { },
    onSwitchChange : (Boolean) -> Unit = {},
    onLogOutClick: () -> Unit = { },
    onViewHistoryClicked: () -> Unit = { },
    onInviteClicked : () -> Unit = { }
){

// CollapsingToolbar Implementation
    val toolbarHeight = 300.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }


    val checkedState = remember { mutableStateOf(true) }


    Scaffold(
        modifier = Modifier
            .fillMaxSize().navigationBarsPadding(),
        topBar = {
            CollapsingToolbarBase(
                toolbarHeading = "Profile",
                toolbarHeight = toolbarHeight,
                toolbarOffset = toolbarOffsetHeightPx.value,
                onBackButtonPressed = onBackButtonPressed
            ) {
                profileHeader(
                userName= authentification.userName,
                email= authentification.userEmail,
                profileLogo = painterResource(id = R.drawable.ic_user),
                    onEditeClicked = onEditeClicked
                )
            }
        },
        bottomBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.MediumPadding.size)) {
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.5f)
                        .padding(4.dp),
                    onClick = onLogOutClick,
                    shape = RoundedCornerShape(14.dp),
                    elevation = ButtonDefaults
                        .elevation(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DoorBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Log Out",
                        color = Color.White,
                        modifier = Modifier
                            .padding(
                                horizontal = 4.dp,
                                vertical = Dimens.ElevationPadding.size
                            ),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }


        }
    ) {
        LazyColumn(
            modifier = modifier
                .nestedScroll(nestedScrollConnection)
                .padding(bottom = Dimens.LargePadding.size)
            ,
            state = listState
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.SmallPadding.size)
                ) {

                    profileItem(
                        title = "Notification",
                        content = if (checkedState.value) "Deactivate the Notifications" else "Activate the Notifications",
                    ){
                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = {
                                checkedState.value = it
                                onSwitchChange(it)
                                              },
                            modifier = it
                        )
                    }

                    profileItem(
                        title = "History",
                        content = "View the history of your activity",
                    ){
                        Button(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            onClick = onViewHistoryClicked,
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(
                                text = "View",
                                color = Color.White,
                                modifier = Modifier
                                    .padding(
                                        horizontal = 4.dp,
                                        vertical = Dimens.ElevationPadding.size
                                    ),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                ),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }


                    profileItem(
                        title = "Link of invitation",
                        content = "Invite your friend",
                    ){
                        Button(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            onClick = onInviteClicked,
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Black,
                                contentColor = Color.White
                            ),
                        ) {
                            Text(
                                text = "Invite",
                                color = Color.White,
                                modifier = Modifier
                                    .padding(
                                        horizontal = 4.dp,
                                        vertical = Dimens.ElevationPadding.size
                                    ),
                                style = TextStyle(
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                ),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }


                }

            }

        }

    }

}

@Composable
fun profileItem(
    modifier: Modifier = Modifier,
    title : String = "Notification",
    content : String = "Activate notifications",
    child : @Composable (Modifier) -> Unit = {}
){

   // val checkedState = remember { mutableStateOf(true) }


    Column(
        modifier = modifier.padding(vertical = Dimens.SmallPadding.size, horizontal =Dimens.MediumPadding.size )
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(top = 4.dp, start = 2.dp, bottom = 8.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            backgroundColor = MaterialTheme.colors.primary,
            shape = RoundedCornerShape(14.dp)
        ) {
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = content,
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(
                            horizontal = Dimens.SmallPadding.size,
                            vertical = Dimens.SmallPadding.size
                        ).align(Alignment.CenterVertically),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )


              /*  Switch(
                    checked = checkedState.value,
                    onCheckedChange = { checkedState.value = it },
                    modifier = Modifier.align(Alignment.CenterVertically)
                )*/
                child(Modifier.align(Alignment.CenterVertically))

            }
            
        }
        
    }



    }




@Composable
fun profileHeader(
    modifier: Modifier = Modifier,
    userName: String,
    email: String,
    profileLogo: Painter,
    onEditeClicked: () -> Unit = { }
){

    Column(
        modifier = modifier.animateContentSize(
            animationSpec = tween(
                300,
                easing = LinearOutSlowInEasing
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .padding(vertical = 8.dp, horizontal = 4.dp)
                .align(Alignment.CenterHorizontally),
            painter = profileLogo,
            contentDescription = stringResource(id = R.string.app_logo)
        )
        Text(
            text = userName,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 4.dp)
        )

        Text(
            text = email,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.subtitle2,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(top = 2.dp, bottom = 8.dp)
        )

        OutlinedButton(
            modifier = Modifier
                .padding(4.dp),
            onClick = onEditeClicked,
            shape = CircleShape,
            border =  BorderStroke(1.dp, MaterialTheme.colors.primary),
            elevation = ButtonDefaults
                .elevation(1.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Edite Profile",
                modifier = Modifier
                    .padding(
                        horizontal = 3.dp,
                        vertical = Dimens.ElevationPadding.size
                    ),
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


}




@Preview
@Composable
private fun profileHeaderPreview() {
    PowerSHTheme {
        profileHeader(
            modifier = Modifier,
        userName= "Akram Bensalem",
        email= "ak.bensalem@esi-sba.dz",
        profileLogo = painterResource(id = R.drawable.ic_user),
        )

    }
}



@Preview
@Composable
fun profileScreenPreview(){
PowerSHTheme() {
    profileScreen()
}
}