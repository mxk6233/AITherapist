package com.serenityai.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serenityai.usecases.*
import java.util.Date

/** UC39: Community Support Circles - UI Screen */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunitySupportCirclesScreen(onNavigateBack: () -> Unit) {
    val useCase = remember { CommunitySupportCirclesUseCase() }
    val userId = "user123" // In production, get from auth
    
    var selectedTab by remember { mutableStateOf(0) }
    var showCreateCircleDialog by remember { mutableStateOf(false) }
    var circleName by remember { mutableStateOf("") }
    var circleDescription by remember { mutableStateOf("") }
    var circleTopic by remember { mutableStateOf("") }
    var isPrivateCircle by remember { mutableStateOf(false) }
    
    var userCircles by remember { mutableStateOf<List<SupportCircle>>(emptyList()) }
    var availableCircles by remember { mutableStateOf<List<SupportCircle>>(emptyList()) }
    var selectedCircle by remember { mutableStateOf<SupportCircle?>(null) }
    var circlePosts by remember { mutableStateOf<List<CirclePost>>(emptyList()) }
    var newPostContent by remember { mutableStateOf("") }
    var showPostDialog by remember { mutableStateOf(false) }
    
    // Load user's circles and available circles
    LaunchedEffect(Unit) {
        userCircles = useCase.getUserSupportCircles(userId)
        availableCircles = useCase.getAvailableCircles()
    }
    
    // Refresh when tab changes
    LaunchedEffect(selectedTab) {
        when (selectedTab) {
            0 -> userCircles = useCase.getUserSupportCircles(userId)
            1 -> availableCircles = useCase.getAvailableCircles()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Community Support Circles") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = { showCreateCircleDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Create Circle")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Tab Row
            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("My Circles") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Discover") }
                )
            }
            
            // Content based on selected tab
            when (selectedTab) {
                0 -> {
                    // My Circles Tab
                    if (userCircles.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    Icons.Default.People,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "You haven't joined any circles yet",
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Button(onClick = { selectedTab = 1 }) {
                                    Text("Discover Circles")
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(userCircles) { circle ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedCircle = circle
                                            circlePosts = useCase.getCirclePosts(circle.id)
                                        },
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = circle.name,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                if (circle.topic != null) {
                                                    Text(
                                                        text = circle.topic,
                                                        fontSize = 12.sp,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                            if (circle.isPrivate) {
                                                Icon(
                                                    Icons.Default.Lock,
                                                    contentDescription = "Private",
                                                    modifier = Modifier.size(20.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = circle.description,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            Text(
                                                text = "${circle.memberCount} members",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = "${useCase.getCirclePosts(circle.id).size} posts",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Discover Tab
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (availableCircles.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Search,
                                            contentDescription = null,
                                            modifier = Modifier.size(64.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            text = "No public circles available",
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Button(onClick = { showCreateCircleDialog = true }) {
                                            Text("Create First Circle")
                                        }
                                    }
                                }
                            }
                        } else {
                            items(availableCircles) { circle ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column(modifier = Modifier.weight(1f)) {
                                                Text(
                                                    text = circle.name,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                if (circle.topic != null) {
                                                    Text(
                                                        text = circle.topic,
                                                        fontSize = 12.sp,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = circle.description,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${circle.memberCount} members",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            val isMember = userCircles.any { it.id == circle.id }
                                            if (isMember) {
                                                TextButton(onClick = {
                                                    useCase.leaveSupportCircle(circle.id, userId)
                                                    userCircles = useCase.getUserSupportCircles(userId)
                                                    availableCircles = useCase.getAvailableCircles()
                                                }) {
                                                    Text("Leave")
                                                }
                                            } else {
                                                Button(onClick = {
                                                    useCase.joinSupportCircle(circle.id, userId)
                                                    userCircles = useCase.getUserSupportCircles(userId)
                                                    availableCircles = useCase.getAvailableCircles()
                                                }) {
                                                    Text("Join")
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Circle Detail Dialog
    if (selectedCircle != null) {
        AlertDialog(
            onDismissRequest = { selectedCircle = null },
            title = { Text(selectedCircle!!.name) },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = selectedCircle!!.description,
                        fontSize = 14.sp
                    )
                    
                    Divider()
                    
                    Text(
                        text = "Posts",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (circlePosts.isEmpty()) {
                        Text(
                            text = "No posts yet. Be the first to post!",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 300.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(circlePosts) { post ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp)
                                    ) {
                                        Text(
                                            text = post.content,
                                            fontSize = 14.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            Text(
                                                text = "${post.likeCount} likes",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = "${post.commentCount} comments",
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Button(
                        onClick = { showPostDialog = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Create Post")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedCircle = null }) {
                    Text("Close")
                }
            }
        )
    }
    
    // Create Circle Dialog
    if (showCreateCircleDialog) {
        AlertDialog(
            onDismissRequest = { showCreateCircleDialog = false },
            title = { Text("Create Support Circle") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = circleName,
                        onValueChange = { circleName = it },
                        label = { Text("Circle Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = circleDescription,
                        onValueChange = { circleDescription = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                    OutlinedTextField(
                        value = circleTopic,
                        onValueChange = { circleTopic = it },
                        label = { Text("Topic (Optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isPrivateCircle,
                            onCheckedChange = { isPrivateCircle = it }
                        )
                        Text(
                            text = "Private Circle (Invite Only)",
                            modifier = Modifier.clickable { isPrivateCircle = !isPrivateCircle }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (circleName.isNotBlank() && circleDescription.isNotBlank()) {
                            val newCircle = useCase.createSupportCircle(
                                creatorId = userId,
                                name = circleName,
                                description = circleDescription,
                                topic = if (circleTopic.isNotBlank()) circleTopic else null,
                                isPrivate = isPrivateCircle
                            )
                            userCircles = useCase.getUserSupportCircles(userId)
                            availableCircles = useCase.getAvailableCircles()
                            circleName = ""
                            circleDescription = ""
                            circleTopic = ""
                            isPrivateCircle = false
                            showCreateCircleDialog = false
                        }
                    },
                    enabled = circleName.isNotBlank() && circleDescription.isNotBlank()
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateCircleDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Create Post Dialog
    if (showPostDialog && selectedCircle != null) {
        AlertDialog(
            onDismissRequest = { showPostDialog = false },
            title = { Text("Create Post") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = newPostContent,
                        onValueChange = { newPostContent = it },
                        label = { Text("What's on your mind?") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newPostContent.isNotBlank() && selectedCircle != null) {
                            try {
                                useCase.createPost(
                                    circleId = selectedCircle!!.id,
                                    userId = userId,
                                    content = newPostContent,
                                    isAnonymous = false
                                )
                                circlePosts = useCase.getCirclePosts(selectedCircle!!.id)
                                newPostContent = ""
                                showPostDialog = false
                            } catch (e: Exception) {
                                // Handle error
                            }
                        }
                    },
                    enabled = newPostContent.isNotBlank()
                ) {
                    Text("Post")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPostDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}







