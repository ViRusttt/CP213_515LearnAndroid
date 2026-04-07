package com.example.a515learnandroid1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a515learnandroid1.ui.theme._515LearnAndroid1Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────
// Data Model
// ─────────────────────────────────────────────

data class Contact(val name: String) {
    val initial: Char get() = name.first().uppercaseChar()
}

// ─────────────────────────────────────────────
// ViewModel
// ─────────────────────────────────────────────

class ContactViewModel : ViewModel() {

    // Mock data seed — one batch of names per letter A-Z
    private val mockDataPool: List<Contact> = buildList {
        val names = mapOf(
            'A' to listOf("Alice Anderson", "Aaron Adams", "Amy Allen"),
            'B' to listOf("Bob Brown", "Betty Baker", "Brian Bell"),
            'C' to listOf("Carol Clark", "Chris Carter", "Cynthia Collins"),
            'D' to listOf("David Davis", "Diana Dixon", "Derek Duncan"),
            'E' to listOf("Eva Evans", "Ethan Edwards", "Emily Ellis"),
            'F' to listOf("Frank Foster", "Fiona Flynn", "Fred Fisher"),
            'G' to listOf("Grace Green", "George Gray", "Gloria Grant"),
            'H' to listOf("Henry Harris", "Hannah Hall", "Howard Hughes"),
            'I' to listOf("Iris Irving", "Ivan Ingram"),
            'J' to listOf("James Jones", "Jessica Jenkins", "John Jackson"),
            'K' to listOf("Karen King", "Kevin Knight", "Katherine Knox"),
            'L' to listOf("Laura Lewis", "Leo Lambert", "Linda Long"),
            'M' to listOf("Mark Miller", "Mary Morgan", "Michael Moore"),
            'N' to listOf("Nancy Nelson", "Nathan Nash", "Nicole Norris"),
            'O' to listOf("Oscar Owen", "Olivia O'Brien"),
            'P' to listOf("Paul Parker", "Patricia Price", "Peter Powell"),
            'Q' to listOf("Quinn Quinn"),
            'R' to listOf("Rachel Reed", "Robert Ross", "Rita Rivera"),
            'S' to listOf("Sarah Scott", "Steven Stone", "Sophie Shaw"),
            'T' to listOf("Thomas Taylor", "Tina Turner", "Todd Thompson"),
            'U' to listOf("Uma Underwood", "Ulysses Upton"),
            'V' to listOf("Victor Vance", "Violet Vega"),
            'W' to listOf("Walter White", "Wendy Wilson", "William Ward"),
            'X' to listOf("Xander Xavier"),
            'Y' to listOf("Yvonne Young", "Yusuf Yates"),
            'Z' to listOf("Zara Zhang", "Zoe Zhou")
        )
        names.forEach { (_, nameList) -> addAll(nameList.map { Contact(it) }) }
    }

    // Extra pages of mock data (just shuffle the pool for demo)
    private val extraPages: List<List<Contact>> = listOf(
        listOf(
            Contact("Amelia Abbott"), Contact("Beatrice Blake"),
            Contact("Charles Chase"), Contact("Dorothy Drake"),
            Contact("Edward Eaton"), Contact("Florence Ford")
        ),
        listOf(
            Contact("Gilbert Gibson"), Contact("Helen Hart"),
            Contact("Ivan Irwin"), Contact("Julia James"),
            Contact("Kenneth Kane"), Contact("Lily Lane")
        ),
        listOf(
            Contact("Malcolm March"), Contact("Nora Newman"),
            Contact("Oliver Olsen"), Contact("Penny Penn"),
            Contact("Quentin Quest"), Contact("Rose Rand")
        )
    )

    private var currentExtraPage = 0
    private var canLoadMore = true

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        // Load initial data (A-Z seed)
        _contacts.value = mockDataPool.sortedBy { it.name }
    }

    fun loadMoreContacts() {
        if (_isLoading.value || !canLoadMore) return

        viewModelScope.launch {
            _isLoading.value = true
            delay(2_000) // Simulate network latency

            if (currentExtraPage < extraPages.size) {
                val newContacts = extraPages[currentExtraPage++]
                _contacts.value = (_contacts.value + newContacts).sortedBy { it.name }
            } else {
                canLoadMore = false // No more data
            }

            _isLoading.value = false
        }
    }
}

// ─────────────────────────────────────────────
// Activity
// ─────────────────────────────────────────────

class Part2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _515LearnAndroid1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// Screen
// ─────────────────────────────────────────────

// Flatten into a list of items: either a Header or a ContactItem
sealed class ListItem {
    data class Header(val letter: Char) : ListItem()
    data class Item(val contact: Contact) : ListItem()
}

@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    vm: ContactViewModel = viewModel()
) {
    val contacts by vm.contacts.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val listState = rememberLazyListState()

    // Group contacts by their first letter for sticky headers
    val grouped: Map<Char, List<Contact>> = remember(contacts) {
        contacts.groupBy { it.initial }.toSortedMap()
    }

    val flatItems: List<ListItem> = remember(grouped) {
        buildList {
            grouped.forEach { (letter, group) ->
                add(ListItem.Header(letter))
                addAll(group.map { ListItem.Item(it) })
            }
        }
    }

    // Detect when user reaches the last item → trigger pagination
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleIndex >= totalItems - 2 && totalItems > 0
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) vm.loadMoreContacts()
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Top bar
        Surface(shadowElevation = 4.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Contacts  (${contacts.size})",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            flatItems.forEach { listItem ->
                when (listItem) {
                    is ListItem.Header -> {
                        stickyHeader(key = "header_${listItem.letter}") {
                            StickyHeaderItem(letter = listItem.letter)
                        }
                    }
                    is ListItem.Item -> {
                        item(key = listItem.contact.name) {
                            ContactRow(contact = listItem.contact)
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 72.dp),
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }
            }

            // Loading indicator / end-of-list message
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Loading more contacts…",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// Sticky Header Composable
// ─────────────────────────────────────────────

@Composable
fun StickyHeaderItem(letter: Char) {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = letter.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

// ─────────────────────────────────────────────
// Contact Row Composable
// ─────────────────────────────────────────────

@Composable
fun ContactRow(contact: Contact) {
    // Generate a deterministic pastel color from the initial
    val avatarColor = remember(contact.initial) {
        val hue = (contact.initial - 'A') * (360f / 26f)
        Color.hsl(hue, 0.55f, 0.60f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar circle
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(avatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = contact.initial.toString(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = contact.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
