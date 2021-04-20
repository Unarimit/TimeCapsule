﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using TimeCapsule.Infrastructure.Persistence;

namespace TimeCapsule.Infrastructure.Persistence.Migrations
{
    [DbContext(typeof(ApplicationDbContext))]
    partial class ApplicationDbContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
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

                    b.Property<string>("Desc")
                        .HasColumnType("varchar(256) CHARACTER SET utf8mb4")
                        .HasMaxLength(256);

                    b.Property<string>("Email")
                        .IsRequired()
                        .HasColumnType("varchar(128) CHARACTER SET utf8mb4")
                        .HasMaxLength(128);

                    b.Property<DateTime>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("Password")
                        .IsRequired()
                        .HasColumnType("varchar(256) CHARACTER SET utf8mb4")
                        .HasMaxLength(256);

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

                    b.Property<string>("Desc")
                        .HasColumnType("longtext CHARACTER SET utf8mb4")
                        .HasMaxLength(2048);

                    b.Property<DateTime>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("Username")
                        .IsRequired()
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

                    b.Property<Guid>("DailyId")
                        .HasColumnType("char(36)");

                    b.Property<DateTime?>("EndTime")
                        .HasColumnType("datetime(6)");

                    b.Property<bool>("IsFinish")
                        .HasColumnType("tinyint(1)");

                    b.Property<DateTime>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<Guid>("TaskId")
                        .HasColumnType("char(36)");

                    b.HasKey("Id");

                    b.HasIndex("DailyId");

                    b.HasIndex("TaskId");

                    b.ToTable("Periods");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTask", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<int>("AchievementPerHour")
                        .HasColumnType("int");

                    b.Property<string>("Desc")
                        .HasColumnType("varchar(256) CHARACTER SET utf8mb4")
                        .HasMaxLength(256);

                    b.Property<DateTime?>("FinishTime")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("Icon")
                        .IsRequired()
                        .HasColumnType("varchar(512) CHARACTER SET utf8mb4")
                        .HasMaxLength(512);

                    b.Property<bool>("IsFinish")
                        .HasColumnType("tinyint(1)");

                    b.Property<DateTime>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasColumnType("varchar(50) CHARACTER SET utf8mb4")
                        .HasMaxLength(50);

                    b.Property<Guid>("TaskClassId")
                        .HasColumnType("char(36)");

                    b.Property<string>("Username")
                        .IsRequired()
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4");

                    b.HasKey("Id");

                    b.HasIndex("TaskClassId");

                    b.HasIndex("Username");

                    b.ToTable("Tasks");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTaskClass", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("char(36)");

                    b.Property<string>("Color")
                        .IsRequired()
                        .HasColumnType("varchar(10) CHARACTER SET utf8mb4")
                        .HasMaxLength(10);

                    b.Property<DateTime>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("Name")
                        .IsRequired()
                        .HasColumnType("varchar(40) CHARACTER SET utf8mb4")
                        .HasMaxLength(40);

                    b.Property<string>("Username")
                        .IsRequired()
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

                    b.Property<string>("Email")
                        .IsRequired()
                        .HasColumnType("varchar(50) CHARACTER SET utf8mb4")
                        .HasMaxLength(50);

                    b.Property<bool>("IsAdmin")
                        .HasColumnType("tinyint(1)");

                    b.Property<bool>("IsUser")
                        .HasColumnType("tinyint(1)");

                    b.Property<DateTime>("LastModified")
                        .HasColumnType("datetime(6)");

                    b.Property<string>("Password")
                        .IsRequired()
                        .HasColumnType("varchar(256) CHARACTER SET utf8mb4")
                        .HasMaxLength(256);

                    b.HasKey("Username");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeDaily", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.User", "User")
                        .WithMany("Dailies")
                        .HasForeignKey("Username")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimePeriod", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.TimeDaily", "Daily")
                        .WithMany("Periods")
                        .HasForeignKey("DailyId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("TimeCapsule.Domain.Entities.TimeTask", "Task")
                        .WithMany("Periods")
                        .HasForeignKey("TaskId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTask", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.TimeTaskClass", "TaskClass")
                        .WithMany("Tasks")
                        .HasForeignKey("TaskClassId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("TimeCapsule.Domain.Entities.User", "User")
                        .WithMany("Tasks")
                        .HasForeignKey("Username")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("TimeCapsule.Domain.Entities.TimeTaskClass", b =>
                {
                    b.HasOne("TimeCapsule.Domain.Entities.User", "User")
                        .WithMany("TaskClasses")
                        .HasForeignKey("Username")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });
#pragma warning restore 612, 618
        }
    }
}
