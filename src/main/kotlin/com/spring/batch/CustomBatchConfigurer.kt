package com.spring.batch

import org.springframework.batch.core.DefaultJobKeyGenerator
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.batch.item.database.support.DefaultDataFieldMaxValueIncrementerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Isolation
import javax.sql.DataSource


//@Configuration
//@EnableBatchProcessing(
//    daft
//)
//class CustomBatchConfigurer(
//    private val dataSource: DataSource,
//    private val platformTransactionManager: PlatformTransactionManager,
//) {
//    @Bean
//    fun createJobRepository(): JobRepository {
//        val factory = JobRepositoryFactoryBean()
//        factory.setDatabaseType("POSTGRES")
//        factory.setDataSource(dataSource)
//        factory.setIncrementerFactory(DefaultDataFieldMaxValueIncrementerFactory(dataSource))
//        factory.setJobKeyGenerator(DefaultJobKeyGenerator())
//        factory.transactionManager = this.platformTransactionManager
//        factory.setIsolationLevelForCreateEnum(Isolation.READ_COMMITTED)
//        factory.setTablePrefix("SYSTEM_")
//        return factory.getObject()
//    }
//}
