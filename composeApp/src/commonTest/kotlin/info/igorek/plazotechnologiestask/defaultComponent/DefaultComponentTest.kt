package info.igorek.plazotechnologiestask.defaultComponent

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import info.igorek.plazotechnologiestask.repository.UserRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.BeforeTest

class MockUserRepository : UserRepository {
    var updateNameCalls = 0
    override val user: Value<User> = MutableValue(User.EMPTY)

    override suspend fun updateName(name: String): User {
        updateNameCalls++
        return User.EMPTY.copy(fullName = name)
    }

    override suspend fun reloadUser(): User = loadProfile()
    override suspend fun loadProfile(): User = User.EMPTY
}

class DefaultComponentTest {
    private lateinit var mockRepository: MockUserRepository
    private lateinit var component: DefaultComponent

    @BeforeTest
    fun setup() {
        mockRepository = MockUserRepository()
        component = DefaultComponent(
            DefaultComponentContext(lifecycle = LifecycleRegistry()),
            mockRepository
        )
    }

    @Test
    fun `test Component Initializes With Empty User`() {
        assertEquals(User.EMPTY, component.state.value.user)
    }

    @Test
    fun `test Update Name Dialog Slots`() {
        assertNull(component.slots.value.child?.instance)

        component.showUpdateNameDialog()
        assertTrue(component.slots.value.child?.instance is DefaultComponent.Child.UpdateName)

        component.dismissUpdateNameDialog()
        assertNull(component.slots.value.child?.instance)
    }

    @Test
    fun `test Update Name With Empty String Is Ignored`() {
        component.onConfirmUpdateNameDialog("")

        assertEquals(0, mockRepository.updateNameCalls)
    }
}
