package com.faisal.quc.core.di
import com.faisal.quc.repository.MarkingImageRepository
import com.faisal.quc.repository.MarkingImageRepositoryImpl
import com.faisal.quc.repository.AuthenticationRepository
import com.faisal.quc.repository.AuthenticationRepositoryImpl
import com.faisal.quc.repository.MainRepository
import com.faisal.quc.repository.MainRepositoryImpl
import com.faisal.quc.repository.PosForSewingLineRepository
import com.faisal.quc.repository.PosForSewingLineRepositoryImpl
import com.faisal.quc.repository.POColorSizeQuantityRepository
import com.faisal.quc.repository.POColorSizeQuantityRepositoryImpl
import com.faisal.quc.repository.LineHistoryRepository
import com.faisal.quc.repository.LineHistoryRepositoryImpl
import com.faisal.quc.repository.QCHistoryRepository
import com.faisal.quc.repository.QCHistoryRepositoryImpl
import com.faisal.quc.repository.QcHomeRepository
import com.faisal.quc.repository.QcHomeRepositoryImp
import com.faisal.quc.repository.SelectPORepository
import com.faisal.quc.repository.SelectPORepositoryImpl
import com.faisal.quc.repository.SewingLineRepository
import com.faisal.quc.repository.SewingLineRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// todo
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    fun provideMarkingImageRepositoryImpl(markingImageRepositoryImpl: MarkingImageRepositoryImpl): MarkingImageRepository

    @Binds
    fun provideSewingLineRepositoryImpl(sewingLineRepositoryImpl: SewingLineRepositoryImpl): SewingLineRepository

    @Binds
    fun providePosForSewingLineRepositoryImpl(posForSewingLineRepositoryImpl: PosForSewingLineRepositoryImpl): PosForSewingLineRepository

    @Binds
    fun providePostSewingLineInRepositoryImpl(postSewingLineInRepositoryImpl: POColorSizeQuantityRepositoryImpl): POColorSizeQuantityRepository

    @Binds
    fun provideSelectPORepository(selectPORepositoryImpl: SelectPORepositoryImpl): SelectPORepository

    @Binds
    fun provideLineHistoryRepository(lineHistoryRepositoryImpl: LineHistoryRepositoryImpl): LineHistoryRepository

    @Binds
    fun provideQCHistoryRepository(qcHistoryRepositoryImpl: QCHistoryRepositoryImpl): QCHistoryRepository

    @Binds
    fun provideMainRepository(homeRepositoryImpl: MainRepositoryImpl ): MainRepository

    @Binds
    fun qcHomeRepository(qcHomeRepositoryImp: QcHomeRepositoryImp ): QcHomeRepository

}