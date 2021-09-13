package com.akram.bensalem.powersh.ui.screens.login

import akram.bensalem.powersh.R
import akram.bensalem.powersh.ui.components.customTextField
import akram.bensalem.powersh.ui.components.loginTabs
import akram.bensalem.powersh.utils.authentification.Authentifier
import android.app.Activity
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@ExperimentalPagerApi
@Composable
fun mainCard(
    navController : NavController,
    pagerState: PagerState,
    scrollState : ScrollState,

    onBackButtonPressed: () -> Unit = { },
    onPageSelected : (Int) -> Unit
) {
    Column(
            modifier = Modifier
                .graphicsLayer {
                    translationY = -scrollState.value * 1f
                }
                .clip(
                    RoundedCornerShape(32.dp).copy(
                        topStart =ZeroCornerSize,
                        topEnd = ZeroCornerSize
                    )
                )
                .background(
                    color = MaterialTheme.colors.primary,
                ),
            ) {
            IconButton(onClick = {
                onBackButtonPressed()
            }) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.graphicsLayer {
                        translationY = +scrollState.value * 1f
                    }
                    )
            }

            Image(
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 4.dp)
                    .size(80.dp)
                    .align(CenterHorizontally)
                ,
                painter = painterResource(id = R.drawable.powersh_without_background),
                contentDescription = "PowerSH icon",
                contentScale = ContentScale.Crop,


                )
            loginTabs(
                modifier = Modifier.align(CenterHorizontally),
                pagerState = pagerState
            ){
                onPageSelected(it)
            }



        }
}



@ExperimentalMaterialApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun forgetPasswordBottomSheet(
    bottomSheetScaffoldState: ModalBottomSheetState,
    fireBaseAuthentification: Authentifier
) {


    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetContent = {

            val emailState = remember {
                mutableStateOf(TextFieldValue(""))
            }

            val mEmailRequester = remember { FocusRequester() }

            val view = LocalView.current

            Column(
                modifier =Modifier.background(Color.White)
            ) {


                Text(
                    text = "Reset Password",
                    fontWeight = FontWeight.Bold,
                    fontSize =20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp, top = 36.dp, bottom = 16.dp)
                )


                customTextField(
                    modifier = Modifier
                        .padding(0.dp, 8.dp)
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp),
                    textFieldModifier = Modifier
                        .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    title = "Email Address",
                    fieldState = emailState,
                    icon = Icons.Outlined.Email,
                    isPassword = false,
                    focusRequester = mEmailRequester,
                    autofillType = AutofillType.EmailAddress,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                    onNext = {
                    },
                    onDone = {
                        view.clearFocus()
                    }
                )



                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = MaterialTheme.colors.primary,
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(start = 24.dp, end = 24.dp, bottom = 56.dp, top = 24.dp)
                        .fillMaxWidth()
                        .align(CenterHorizontally)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = RoundedCornerShape(18.dp)
                        ),
                    onClick = {
                        fireBaseAuthentification.reinstalisationDeMotDePass(emailState.value.text)
                    }) {
                    Text(
                        text = "Reset",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.padding(
                            start = 24.dp,
                            end = 24.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                    )
                }


            }

        }) {

    }



}




@ExperimentalPagerApi
@ExperimentalMaterialApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun authentificationScreen(
    navController : NavController,
    authentification : Authentifier = Authentifier(LocalContext.current as Activity),
    onBackButtonPressed: () -> Unit = { },
    onLogged: () -> Unit = {}
){


    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val pagerState = rememberPagerState(pageCount = 2)
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background( Color.White),
    ) {

Column(modifier = Modifier
    .fillMaxSize()
    .background( Color.White)
) {
    mainCard(
        navController = navController,
        pagerState = pagerState,
        scrollState = scrollState,
        onBackButtonPressed = onBackButtonPressed,
    ){
        scope.launch {
            pagerState.animateScrollToPage(it, 0f, skipPages = false)

        }
    }

    HorizontalPager(
        state = pagerState,
        itemSpacing = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp)
    ) { page ->

        if (page == 0){
            LoginScreen(
                bottomSheetScaffoldState =bottomSheetScaffoldState,
                modifier =  Modifier,
                fireBaseAuthentification = authentification,
                onLoggin = onLogged
            )
        }else{
            signUpScreen(
                scrollState = scrollState,
                modifier =  Modifier,
                fireBaseAuthentification = authentification,
                onBackToMainScreen = onBackButtonPressed,
            )

        }

    }

}



        forgetPasswordBottomSheet(bottomSheetScaffoldState = bottomSheetScaffoldState, fireBaseAuthentification = authentification )

    }


















}




@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.autofill(
    autofillTypes: List<AutofillType>,
    onFill: ((String) -> Unit),
) = composed {
    val autofill = LocalAutofill.current
    val autofillNode = AutofillNode(onFill = onFill, autofillTypes = autofillTypes)
    LocalAutofillTree.current += autofillNode

    this
        .onGloballyPositioned {
            autofillNode.boundingBox = it.boundsInWindow()
        }
        .onFocusChanged { focusState ->
            autofill?.run {
                if (focusState.isFocused) {
                    requestAutofillForNode(autofillNode)
                } else {
                    cancelAutofillForNode(autofillNode)
                }
            }
        }
}




@ExperimentalPagerApi
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun LoginPage1() {

    val navController = rememberNavController()

    authentificationScreen(navController =navController)

}