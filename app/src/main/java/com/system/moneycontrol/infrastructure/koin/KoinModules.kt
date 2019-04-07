package com.system.moneycontrol.infrastructure.koin

import com.system.moneycontrol.data.repositories.*
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.ui.presentation.home.HomeContract
import com.system.moneycontrol.ui.presentation.home.HomePresenter
import com.system.moneycontrol.ui.presentation.search.SearchContract
import com.system.moneycontrol.ui.presentation.search.SearchPresenter
import com.system.moneycontrol.ui.presentation.tag.TagManagerContract
import com.system.moneycontrol.ui.presentation.tag.TagManagerPresenter
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerPresenter
import com.system.moneycontrol.ui.presentation.typemanager.TypeManagerContract
import com.system.moneycontrol.ui.presentation.typemanager.TypeManagerPresenter
import org.koin.dsl.module.module

object KoinModules {

    val firebaseModule = module {
        single(name = ConstantsDI.FIRESTORE_TAG) { FirebaseFactory.instance().collection("tags") }
        single(name = ConstantsDI.FIRESTORE_TAGGROUP) { FirebaseFactory.instance().collection("tagGroup") }
        single(name = ConstantsDI.FIRESTORE_PAYMENTTYPE) { FirebaseFactory.instance().collection("types") }
        single(name = ConstantsDI.FIRESTORE_TRANSACTION) { FirebaseFactory.instance().collection("transactions") }
    }

    val repositoryModule = module {
        single { TagRepository(collection = get(name = ConstantsDI.FIRESTORE_TAG)) }
        single { TagGroupRepository(collection = get(name = ConstantsDI.FIRESTORE_TAGGROUP)) }
        single { TypeRepository(collection = get(name = ConstantsDI.FIRESTORE_PAYMENTTYPE)) }
        single { TransactionRepository(collection = get(name = ConstantsDI.FIRESTORE_TRANSACTION)) }
    }

    val businessModule = module {
        single { TagBusiness(repository = get(), groupRepository = get()) }
        single { TypeBusiness(repository = get()) }
        single { TransactionBusiness(repository = get(), tagBusiness = get(), typeBusiness = get()) }
        single { HomeBusiness(repTransaction = get(), tagBusiness = get(), typeBusiness = get(), repTagGroup = get()) }
    }

    val presenterModule = module {

        factory { (view: HomeContract.View) ->
            HomePresenter(view = view, business = get(), transactionBusiness = get())
                    as HomeContract.Presenter
        }

        factory { (view: TagManagerContract.View) ->
            TagManagerPresenter(view = view, tagBusiness = get())
                    as TagManagerContract.Presenter
        }

        factory { (view: TypeManagerContract.View) ->
            TypeManagerPresenter(view = view, typeBusiness = get())
                    as TypeManagerContract.Presenter
        }

        factory { (view: TransactionManagerContract.View) ->
            TransactionManagerPresenter(view = view, typeBusiness = get(), tagBusiness = get(), transactionBusiness = get())
                    as TransactionManagerContract.Presenter
        }

        factory { (view: SearchContract.View) ->
            SearchPresenter(view = view, tagBusiness = get(), typeBusiness = get())
                    as SearchContract.Presenter
        }
    }

}