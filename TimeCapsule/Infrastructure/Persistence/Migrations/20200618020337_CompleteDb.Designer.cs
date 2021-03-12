﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using TimeCapsule.Infrastructure.Persistence;

namespace TimeCapsule.Infrastructure.Persistence.Migrations
{
    [DbContext(typeof(ApplicationDbContext))]
    [Migration("20200618020337_CompleteDb")]
    partial class CompleteDb
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "3.1.4")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("TimeCapsule.Domain.Entities.RegisterRequest", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<DateTime>("Created")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("CreatedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Desc")
                        .HasColumnType("longtext CHARACTER SET utf8mb4");

                    b.Property<string>("Email")
                        .HasColumnType("longtext CHARACTER SET utf8mb4");

                    b.Property<DateTime?>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("LastModifiedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Password")
                        .HasColumnType("longtext CHARACTER SET utf8mb4");

                    b.Property<int>("Status")
                        .HasColumnType("int");

                    b.Property<string>("Username")
                        .IsRequired()
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.HasKey("Id");

                    b.ToTable("RegisterRequests");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeDaily", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<int>("Calendar")
                        .HasColumnType("int");

                    b.Property<DateTime>("Created")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("CreatedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Desc")
                        .HasColumnType("longtext CHARACTER SET utf8mb4")
                        .HasMaxLength(2048);

                    b.Property<DateTime?>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("LastModifiedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Username")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4");

                    b.HasKey("Id");

                    b.HasIndex("Username");

                    b.ToTable("Dailies");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimePeriod", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<int>("Assess")
                        .HasColumnType("int");

                    b.Property<DateTime>("BeginTime")
                        .HasColumnType("datetime(6)");

                    b.Property<DateTime>("Created")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("CreatedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<DateTime>("EndTime")
                        .HasColumnType("datetime(6)");

                    b.Property<DateTime?>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("LastModifiedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<Guid?>("TaskId")
                        .HasColumnType("char(36)");

                    b.Property<Guid?>("TimeDailyId")
                        .HasColumnType("char(36)");

                    b.HasKey("Id");

                    b.HasIndex("TaskId");

                    b.HasIndex("TimeDailyId");

                    b.ToTable("Periods");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeSubTask", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<int>("Achievement")
                        .HasColumnType("int");

                    b.Property<DateTime>("Created")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("CreatedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<bool>("IsMulti")
                        .HasColumnType("tinyint(1)");

                    b.Property<DateTime?>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("LastModifiedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Name")
                        .HasColumnType("varchar(50) CHARACTER SET utf8mb4")
                        .HasMaxLength(50);

                    b.Property<int>("Progress")
                        .HasColumnType("int");

                    b.Property<int>("TargetProgress")
                        .HasColumnType("int");

                    b.Property<Guid?>("TaskId")
                        .HasColumnType("char(36)");

                    b.HasKey("Id");

                    b.HasIndex("TaskId");

                    b.ToTable("SubTasks");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeSubTaskRecord", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<int>("IncreProgress")
                        .HasColumnType("int");

                    b.Property<Guid?>("PeriodId")
                        .HasColumnType("char(36)");

                    b.HasKey("Id");

                    b.HasIndex("PeriodId");

                    b.ToTable("SubTaskRecords");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTask", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<int>("AchievementPerHour")
                        .HasColumnType("int");

                    b.Property<Guid?>("ClassId")
                        .HasColumnType("char(36)");

                    b.Property<DateTime>("Created")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("CreatedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Desc")
                        .HasColumnType("varchar(256) CHARACTER SET utf8mb4")
                        .HasMaxLength(256);

                    b.Property<string>("Icon")
                        .HasColumnType("varchar(512) CHARACTER SET utf8mb4")
                        .HasMaxLength(512);

                    b.Property<DateTime?>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("LastModifiedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Name")
                        .HasColumnType("varchar(50) CHARACTER SET utf8mb4")
                        .HasMaxLength(50);

                    b.Property<string>("Username")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4");

                    b.HasKey("Id");

                    b.HasIndex("ClassId");

                    b.HasIndex("Username");

                    b.ToTable("Tasks");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTaskClass", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<string>("Color")
                        .HasColumnType("varchar(10) CHARACTER SET utf8mb4")
                        .HasMaxLength(10);

                    b.Property<DateTime>("Created")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("CreatedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<DateTime>("DeadLine")
                        .HasColumnType("datetime(6)");

                    b.Property<bool>("IsCounting")
                        .HasColumnType("tinyint(1)");

                    b.Property<DateTime?>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("LastModifiedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Name")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Username")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4");

                    b.HasKey("Id");

                    b.HasIndex("Username");

                    b.ToTable("TaskClasses");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.User", b =>
                {
                    b.Property<string>("Username")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<DateTime>("Created")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("CreatedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Email")
                        .IsRequired()
                        .HasColumnType("varchar(50) CHARACTER SET utf8mb4")
                        .HasMaxLength(50);

                    b.Property<bool>("IsAdmin")
                        .HasColumnType("tinyint(1)");

                    b.Property<bool>("IsUser")
                        .HasColumnType("tinyint(1)");

                    b.Property<DateTime?>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("LastModifiedBy")
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Password")
                        .IsRequired()
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.HasKey("Username");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeDaily", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.User", "User")
                        .WithMany()
                        .HasForeignKey("Username");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimePeriod", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.TimeTask", "Task")
                        .WithMany()
                        .HasForeignKey("TaskId");

                    b.HasOne("TimeCapsule.Domain.Entities.TimeDaily", null)
                        .WithMany("Periods")
                        .HasForeignKey("TimeDailyId");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeSubTask", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.TimeTask", "Task")
                        .WithMany("SubTasks")
                        .HasForeignKey("TaskId");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeSubTaskRecord", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.TimePeriod", "Period")
                        .WithMany("SubTaskRecords")
                        .HasForeignKey("PeriodId");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTask", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.TimeTaskClass", "Class")
                        .WithMany("Tasks")
                        .HasForeignKey("ClassId");

                    b.HasOne("TimeCapsule.Domain.Entities.User", "User")
                        .WithMany()
                        .HasForeignKey("Username");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTaskClass", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.User", "User")
                        .WithMany()
                        .HasForeignKey("Username");
                });
#pragma warning restore 612, 618
        }
    }
}
