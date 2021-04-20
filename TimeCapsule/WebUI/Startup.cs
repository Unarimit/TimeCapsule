using System;
using TimeCapsule.Application;
using TimeCapsule.Infrastructure;
using TimeCapsule.Infrastructure.Persistence;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.WebUI.Services;
using TimeCapsule.WebUI.Filters;

namespace TimeCapsule.WebUI
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public const string CookieScheme = "MrCookie";
        public IConfiguration Configuration { get; }

        // auto: This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            // 两层的DependencyInjection
            services.AddApplication();
            services.AddInfrastructure(Configuration);

            services.AddControllers(o => {  
                o.Filters.Add<CustomExceptionAttribute>();
            });

            services.AddRazorPages();

            //意义不明
            services.AddHealthChecks()
                .AddDbContextCheck<ApplicationDbContext>();

            //注册get current用户服务
            services.AddHttpContextAccessor();
            services.AddScoped<ICurrentUserService, CurrentUserService>();

            //认证
            services.AddAuthentication(CookieScheme) // Sets the default scheme to cookies
                .AddCookie(CookieScheme, options =>
                {
                    options.AccessDeniedPath = "/api/identify/account/forbid";
                    options.LoginPath = "/api/identify/account/forbid";
                    options.ExpireTimeSpan = new TimeSpan(7, 0, 0, 0);
                });

            services
                .AddAuthorization(option =>
                {
                    option.AddPolicy("admin", policy => policy.RequireClaim("myadmin")
                        .RequireAuthenticatedUser());

                    option.AddPolicy("user", policy => policy.RequireClaim("myuser")
                        .RequireAuthenticatedUser());
                });
            services
                .AddOpenApiDocument(settings =>
                {
                    settings.Title = "系统管理端";
                    settings.DocumentName = "admin";
                    settings.ApiGroupNames = new string[] {"admin"};
                })
                .AddOpenApiDocument(settings => {
                    settings.Title = "认证端";
                    settings.DocumentName = "identify";
                    settings.ApiGroupNames = new string[] { "identify" };
                })
                .AddOpenApiDocument(settings => {
                     settings.Title = "用户端";
                     settings.DocumentName = "user";
                     settings.ApiGroupNames = new string[] { "user" };
                })
                .AddOpenApiDocument(settings => {
                    settings.Title = "调试";
                    settings.DocumentName = "debug";
                    settings.ApiGroupNames = new string[] { "debug" };
                });
        }

        // auto: This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Error");
                // auto: The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();
           
            app.UseOpenApi();
            app.UseSwaggerUi3();

            app.UseRouting();

            app.UseAuthentication();
            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                // endpoints.MapControllers(); map all
                endpoints.MapControllerRoute(name:default, pattern:"{controller}/{action=index}/{id?}"); // map pattern
                endpoints.MapRazorPages();
            });
        }
    }
}
